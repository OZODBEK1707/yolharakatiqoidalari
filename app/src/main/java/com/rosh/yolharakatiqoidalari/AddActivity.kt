package com.rosh.yolharakatiqoidalari

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.R
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.rosh.yolharakatiqoidalari.databinding.ActivityAddBinding
import com.rosh.yolharakatiqoidalari.db.MyDbHelper
import com.rosh.yolharakatiqoidalari.models.Turkum.TEMP_USER
import com.rosh.yolharakatiqoidalari.models.Turkum.USER_EDITED_STATE
import com.rosh.yolharakatiqoidalari.models.Turkum.VIEW_PAGER_ITEM_POSITION
import com.rosh.yolharakatiqoidalari.models.Turkum.WHICH_TYPE_ARRAY
import com.rosh.yolharakatiqoidalari.models.User
import java.io.File
import java.io.FileOutputStream

class AddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var user: User
    private lateinit var photoUri: Uri
    private lateinit var photoPath: String
    private lateinit var lastUserInDb: User
    private lateinit var name: String
    private lateinit var about: String
    private var whichTypePosition = 0
    private var whichTypeState = false
    private var photoSelectedState = false
    private var isEdit = false
    private var databaseState = false
    private var imageFileName = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        doDefault()
        myDbHelper = MyDbHelper(this)
        if (myDbHelper.getAllUsers().isEmpty()) {
            databaseState = true
        } else {
            lastUserInDb = myDbHelper.getAllUsers().last()
        }


        checkForEdit()


        binding.image.setOnClickListener {
            getImageContent.launch("image/*")
        }


        binding.btnSave.setOnClickListener {
            name = binding.edtName.text.toString().trim()
            about = binding.edtAbout.text.toString().trim()


            if (isEdit) {
                editUser()
            } else {
                addUser()
            }


        }

    }

    private fun editUser() {
        if (name.isNotEmpty()) {
            /** checking for new image selectedState */
            if (photoSelectedState) {
                val inputStream = contentResolver?.openInputStream(photoUri)
                val file = File(filesDir, "${user.id}.jpg")
                photoPath = file.absolutePath
                val fileOutputStream = FileOutputStream(file)
                inputStream?.copyTo(fileOutputStream)
                inputStream?.close()
                fileOutputStream.close()


                user.photoPath = photoPath
            }

            user.name = name
            user.about = about
            user.whichType = whichTypePosition


            myDbHelper.editUser(user)

            USER_EDITED_STATE = true
            TEMP_USER = user
            Toast.makeText(this, "Saqlandi", Toast.LENGTH_SHORT).show()
            VIEW_PAGER_ITEM_POSITION = user.whichType!!
            finish()
        }
    }

    private fun addUser() {
        if (whichTypeState && name.isNotEmpty() && photoSelectedState) {

            savePhotoToDir()

            user = User(name, about, whichTypePosition, 0, photoPath)
            myDbHelper.addUser(user)

            Toast.makeText(this@AddActivity, "Saqlandi", Toast.LENGTH_SHORT).show()
            VIEW_PAGER_ITEM_POSITION = user.whichType!!
            finish()
        } else {
            Toast.makeText(this@AddActivity, "To'ldiring", Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkForEdit() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        if (isEdit) {
            user = intent.getSerializableExtra("user") as User
            binding.apply {
                edtName.setText(user.name)
                edtAbout.setText(user.about)
                spinnerWhichType.setText(WHICH_TYPE_ARRAY[user.whichType!!])
                image.setImageURI(Uri.parse(user.photoPath))
                whichTypePosition = user.whichType!!
                val adapter2 = ArrayAdapter<String>(
                    this@AddActivity, R.layout.simple_spinner_dropdown_item, WHICH_TYPE_ARRAY
                )
                spinnerWhichType.setAdapter(adapter2)

            }
        }
    }

    private fun savePhotoToDir() {
        imageFileName = if (databaseState) {
            1
        } else {
            lastUserInDb.id!! + 1
        }
        val inputStream = contentResolver?.openInputStream(photoUri)
        val file = File(filesDir, "$imageFileName.jpg")
        photoPath = file.absolutePath
        val fileOutputStream = FileOutputStream(file)
        inputStream?.copyTo(fileOutputStream)
        inputStream?.close()
        fileOutputStream.close()
    }

    private fun doDefault() {
        val adapter = ArrayAdapter<String>(
            this, R.layout.simple_spinner_dropdown_item, WHICH_TYPE_ARRAY
        )
        binding.apply {
            toolbar1.setNavigationOnClickListener {
                finish()
            }
            spinnerWhichType.setAdapter(adapter)

            spinnerWhichType.setOnItemClickListener { _, _, i, _ ->
                whichTypePosition = i
                whichTypeState = true

            }
        }

    }

    private val getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it ?: return@registerForActivityResult
        binding.image.setImageURI(it)
        photoUri = it
        photoSelectedState = true
    }
}