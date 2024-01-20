package com.example.libraryapp

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.libraryapp.data.person.Person
import com.example.libraryapp.data.person.PersonRepository
import com.example.libraryapp.databinding.ActivityMainBinding
import com.example.libraryapp.ui.notifications.PersonsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var repository: PersonRepository
    private lateinit var binding: ActivityMainBinding
    private val CONTACTS_PERMISSION_CODE = 123
    lateinit var persons: MutableList<Person>
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        progressBar = binding.progressBar
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_rents, R.id.navigation_persons, R.id.navigation_positions
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        readContactsPermission()
    }

    private fun readContactsPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                CONTACTS_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CONTACTS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    lifecycleScope.launch { loadContacts() }
                   // smsPermissions()
                } else {
                    showNecessityPermissionsDialog()
                }
            }
            //MY_PERMISSIONS_REQUEST_SEND_SMS -> { callPermissions() }
        }
    }

    private suspend fun loadContacts() {
        val contentResolver: ContentResolver = this.contentResolver
        withContext(Dispatchers.IO) {
            val deffered = async { fetchContacts(contentResolver) }
            val loadComplete = deffered.await()
            if (loadComplete) {
                loadData()
                withContext(Dispatchers.Main) {
                    onDataLoaded()
                }
            }
        }

    }

    private fun savePersons() {
        lifecycleScope.launch {
            try {
//                for (i in persons) {
//                    repository.insert(i)
//                }
                repository.insert(persons)
            } catch (e: Exception) {
                Log.e("SavePersons", "Error saving persons: ${e.message}", e)
            }
        }
    }

    @SuppressLint("Range")
    fun fetchContacts(contentResolver: ContentResolver): Boolean {
        val contacts = mutableListOf<Person>()

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (cursor.moveToNext()) {

                val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val contactPhoneNumber = getContactPhoneNumber(contentResolver, contactId)
                val photo = getContactPhoto(contentResolver,contactId)

                val contact = Person(contactName, contactPhoneNumber?:"",  photo)
                contacts.add(contact)
            }
        }

        cursor?.close()
        persons = contacts
        return true
    }

    @SuppressLint("Range")
    private fun getContactPhoneNumber(contentResolver: ContentResolver, contactId: String): String? {
        val phones = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
            null,
            null
        )

        var phoneNumber: String? = null

        phones?.use {
            if (phones.moveToFirst()) {
                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
        }

        phones?.close()

        return phoneNumber
    }

    @SuppressLint("Range")
    private fun getContactPhoto(contentResolver: ContentResolver, contactId: String): Bitmap? {
        val photoUri: Uri? = ContentUris.withAppendedId(
            ContactsContract.Contacts.CONTENT_URI,
            contactId.toLong()
        )
        return try {
            val thumbnail = ContactsContract.Contacts.openContactPhotoInputStream(
                contentResolver, photoUri!!, true
            )
            if (thumbnail != null)
                BitmapFactory.decodeStream(thumbnail)
            else
                null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun onDataLoaded() {
        val fragment = supportFragmentManager.findFragmentById(R.id.navigation_persons) as? PersonsFragment
        fragment?.onResume()
    }

    private fun showNecessityPermissionsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Uwaga!")
            .setMessage("Kontakty nie zostały pobrane!")
            .setNegativeButton("Zamknij") { _, _ -> finish() }
            .setPositiveButton("Przejdź do zgody") { _, _ -> readContactsPermission() }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun loadData() {
        // Uruchomienie korutyny na głównym wątku
        CoroutineScope(Dispatchers.Main).launch {
            // Symulacja zadania trwającego dłuższy czas
            withContext(Dispatchers.IO) {
                savePersons()
            }

            // Ukryj pasek postępu po zakończeniu ładowania danych
            progressBar.visibility = View.GONE
        }
    }
}