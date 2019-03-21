package vn.nsn.app.ocb.helper

import android.content.Context
import android.support.v7.app.AlertDialog
import vn.nsn.app.ocb.R

class DialogHelper(private var context: Context) {

    fun showAlertDialog(title: String, message: String, runnable: Runnable) {
        val builder = AlertDialog.Builder(context)

        // Set the alert dialog title
        builder.setTitle(title)

        // Display a message on alert dialog
        builder.setMessage(message)

        // Set a positive button and its click listener on alert dialog
//        builder.setPositiveButton(context.getString(R.string.ok)) { dialog, which ->
//            runnable.run()
//        }

        // Display a negative button on alert dialog
//        builder.setNegativeButton(context.getString(R.string.cancel)) { dialog, which ->
//
//        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    fun showAlertDialog(title: String, message: String, ok: Runnable, cancel: Runnable) {
        val builder = AlertDialog.Builder(context)

        // Set the alert dialog title
        builder.setTitle(title)

        // Display a message on alert dialog
        builder.setMessage(message)

        // Set a positive button and its click listener on alert dialog
//        builder.setPositiveButton(context.getString(R.string.ok)) { dialog, which ->
//            ok.run()
//        }

        // Display a negative button on alert dialog
//        builder.setNegativeButton(context.getString(R.string.cancel)) { dialog, which ->
//            cancel.run()
//        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    fun showAlertDialogYesNo(title: String, message: String, runnable: Runnable){
        val builder = AlertDialog.Builder(context)

        // Set the alert dialog title
        builder.setTitle(title)

        // Display a message on alert dialog
        builder.setMessage(message)

        // Set a positive button and its click listener on alert dialog
//        builder.setPositiveButton(context.getString(R.string.yes)) { dialog, which ->
//            runnable.run()
//        }

        // Display a negative button on alert dialog
//        builder.setNegativeButton(context.getString(R.string.no)) { dialog, which ->
//
//        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }
}
