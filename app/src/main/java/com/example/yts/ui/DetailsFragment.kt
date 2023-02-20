package com.example.yts.ui

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.work.*
import com.example.yts.R
import com.example.yts.databinding.FragmentDetailsBinding
import com.example.yts.utils.*
import com.squareup.picasso.Picasso


class DetailsFragment: Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val navArgs: DetailsFragmentArgs by navArgs()

    lateinit var workManager: WorkManager

    private var qualityToBeDownloaded: String = ""
    private val pushNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        startDownloadingFile()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = navArgs.movie.title

        workManager = WorkManager.getInstance(requireContext())
        bindData()

    }


    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pushNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            startDownloadingFile()
        }
    }

    private fun startDownloadingFile(
    ) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()
        val data = Data.Builder()

        data.apply {
            when(qualityToBeDownloaded) {
                "720" ->{
                    putString(KEY_FILE_URL, navArgs.movie.torrents[0].url)
                    putString(KEY_FILE_NAME, "${navArgs.movie.title}_720P.torrent")
                }
                "1080" ->{
                    putString(KEY_FILE_URL, navArgs.movie.torrents[1].url)
                    putString(KEY_FILE_NAME, "${navArgs.movie.title}_1080P.torrent")
                }
            }
            putString(KEY_FILE_TYPE, "torrent")
        }

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(FileDownloadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        workManager.enqueue(oneTimeWorkRequest)

    }




    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
    }


    private fun bindData() {
        Picasso.get().load(navArgs.movie.large_cover_image)
            .into(binding.movieImage)
        binding.movieYear.text = navArgs.movie.year.toString()
        binding.movieGenre.text = navArgs.movie.genres.joinToString(separator = ",")
        binding.movieRating.text = "${navArgs.movie.rating}/10"
        binding.movieSynoposis.text = navArgs.movie.synopsis

        binding.quality720.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Download Torrent")
                .setMessage("Are you sure you want to download ${navArgs.movie.torrents[0].size} size torrent file?")
                .setPositiveButton("Download"
                ) { dialog, which ->
                    qualityToBeDownloaded = "720"
                    checkNotificationPermission()
                }
                .setNegativeButton("Copy Magnet") { dialog, which ->
                    val clipboard: ClipboardManager =
                        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData. newPlainText("torrent_file", getMagnetLink(navArgs.movie.torrents[0].hash))
                    clipboard.setPrimaryClip(clip)
                    showMsg("Copied")
                }
                .show()
        }
        binding.quality1080.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Download Torrent")
                .setMessage("Are you sure you want to download ${navArgs.movie.torrents[1].size} size torrent file?")
                .setPositiveButton("Download"
                ) { dialog, which ->
                    qualityToBeDownloaded = "1080"
                    checkNotificationPermission()
                }
                .setNegativeButton("Copy Magnet"){ dialog, which ->
                    val clipboard: ClipboardManager =
                        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData. newPlainText("torrent_file", getMagnetLink(navArgs.movie.torrents[1].hash))
                    clipboard.setPrimaryClip(clip)
                    showMsg("Copied")
                }
                .show()
        }
    }

}