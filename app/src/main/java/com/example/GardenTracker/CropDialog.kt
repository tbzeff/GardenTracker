package com.example.GardenTracker

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.GardenTracker.model.Crop

private const val NEW_CROP = "new-crop"
private const val EDIT_CROP = "edit-crop"

class CropDialog : DialogFragment() {

    private var listener: OnAddCropDialogInteraction? = null

    private val TAG = "ADD_CROP_DIALOG"

    private var mEditCrop: Crop? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { it ->

            arguments?.let {
                mEditCrop = it.getSerializable(EDIT_CROP) as Crop
            }

            // Get Builder
            val builder = AlertDialog.Builder(it)
            // Inflate custom layout
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_crop_dialog, null)
            val nameInput: EditText = view.findViewById(R.id.crop_name_input)
            val typeInput: Spinner = view.findViewById(R.id.crop_type_input)
            val harvestInput: EditText = view.findViewById(R.id.crop_growth_input)
            val waterLabel: TextView = view.findViewById(R.id.waterHoursLabel)
            val waterInput: Spinner = view.findViewById(R.id.water_hours_input)
            val undoButton: Button = view.findViewById(R.id.undo_hour_button)

            var isTouched: Boolean = false

            var waterHours: ArrayList<Int> = ArrayList()

            var dialogTitle = ""
            var posBtnMsg = ""

            if (mEditCrop != null) {
                // Fill in dialog w/ current data
                nameInput.setText(mEditCrop!!.name)
                when (mEditCrop!!.type) {
                    "Flower" -> typeInput.setSelection(0)
                    "Herb" -> typeInput.setSelection(1)
                    "Vegetable" -> typeInput.setSelection(2)
                    "Fruit" -> typeInput.setSelection(3)
                }
                harvestInput.setText("${mEditCrop!!.growthTime}")
                waterHours = mEditCrop!!.waterHoursFromString()
                waterHours.forEach {
                    when (it) {
                        0 -> waterLabel.text = "${waterLabel.text}\n12:00AM"
                        1 -> waterLabel.text = "${waterLabel.text}\n1:00AM"
                        2 -> waterLabel.text = "${waterLabel.text}\n2:00AM"
                        3 -> waterLabel.text = "${waterLabel.text}\n3:00AM"
                        4 -> waterLabel.text = "${waterLabel.text}\n4:00AM"
                        5 -> waterLabel.text = "${waterLabel.text}\n5:00AM"
                        6 -> waterLabel.text = "${waterLabel.text}\n6:00AM"
                        7 -> waterLabel.text = "${waterLabel.text}\n7:00AM"
                        8 -> waterLabel.text = "${waterLabel.text}\n8:00AM"
                        9 -> waterLabel.text = "${waterLabel.text}\n9:00AM"
                        10 -> waterLabel.text = "${waterLabel.text}\n10:00AM"
                        11 -> waterLabel.text = "${waterLabel.text}\n11:00AM"
                        12 -> waterLabel.text = "${waterLabel.text}\n12:00PM"
                        13 -> waterLabel.text = "${waterLabel.text}\n1:00PM"
                        14 -> waterLabel.text = "${waterLabel.text}\n2:00PM"
                        15 -> waterLabel.text = "${waterLabel.text}\n3:00PM"
                        16 -> waterLabel.text = "${waterLabel.text}\n4:00PM"
                        17 -> waterLabel.text = "${waterLabel.text}\n5:00PM"
                        18 -> waterLabel.text = "${waterLabel.text}\n6:00PM"
                        19 -> waterLabel.text = "${waterLabel.text}\n7:00PM"
                        20 -> waterLabel.text = "${waterLabel.text}\n8:00PM"
                        21 -> waterLabel.text = "${waterLabel.text}\n9:00PM"
                        22 -> waterLabel.text = "${waterLabel.text}\n10:00PM"
                        23 -> waterLabel.text = "${waterLabel.text}\n11:00PM"
                    }
                }

                if (waterLabel.text != "") {
                    undoButton.visibility = View.VISIBLE
                }

                dialogTitle = getString(R.string.EditCropDialogTitle, mEditCrop!!.name)
                posBtnMsg = getString(R.string.EdButt)
            } else {
                dialogTitle = getString(R.string.NewCropDialogTitle)
                posBtnMsg = getString(R.string.PosButt)
            }

            // Set click listener for undo add hour button
            undoButton.setOnClickListener {
                if (0 < waterHours.size) {
                    waterHours.removeLast()
                    waterLabel.text = waterLabel.text.dropLastWhile { c -> c != '\n' }
                    waterLabel.text = waterLabel.text.dropLast(1)
                }
                if (0 == waterHours.size) {
                    undoButton.visibility = View.INVISIBLE
                }
            }

            // Set touch listener for waterInput spinner
            waterInput.setOnTouchListener { v, _ ->
                v.performClick()
                isTouched = true
                false
            }

            // Set item select listener for waterInput spinner
            waterInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (!isTouched) return
                    if (parent != null) {
                        Log.d(TAG, "Spinner item ${parent.getItemAtPosition(position)} selected.")
                        val selectedTime = parent.getItemAtPosition(position).toString()
                        waterLabel.text = "${waterLabel.text}\n$selectedTime"
                        when (selectedTime) {
                            parent.getItemAtPosition(0) -> waterHours.add(0)
                            parent.getItemAtPosition(1) -> waterHours.add(1)
                            parent.getItemAtPosition(2) -> waterHours.add(2)
                            parent.getItemAtPosition(3) -> waterHours.add(3)
                            parent.getItemAtPosition(4) -> waterHours.add(4)
                            parent.getItemAtPosition(5) -> waterHours.add(5)
                            parent.getItemAtPosition(6) -> waterHours.add(6)
                            parent.getItemAtPosition(7) -> waterHours.add(7)
                            parent.getItemAtPosition(8) -> waterHours.add(8)
                            parent.getItemAtPosition(9) -> waterHours.add(9)
                            parent.getItemAtPosition(10) -> waterHours.add(10)
                            parent.getItemAtPosition(11) -> waterHours.add(11)
                            parent.getItemAtPosition(12) -> waterHours.add(12)
                            parent.getItemAtPosition(13) -> waterHours.add(13)
                            parent.getItemAtPosition(14) -> waterHours.add(14)
                            parent.getItemAtPosition(15) -> waterHours.add(15)
                            parent.getItemAtPosition(16) -> waterHours.add(16)
                            parent.getItemAtPosition(17) -> waterHours.add(17)
                            parent.getItemAtPosition(18) -> waterHours.add(18)
                            parent.getItemAtPosition(19) -> waterHours.add(19)
                            parent.getItemAtPosition(20) -> waterHours.add(20)
                            parent.getItemAtPosition(21) -> waterHours.add(21)
                            parent.getItemAtPosition(22) -> waterHours.add(22)
                            parent.getItemAtPosition(23) -> waterHours.add(23)
                        }
                    }
                    undoButton.visibility = View.VISIBLE
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }


            // Build dialog
            builder.setMessage(dialogTitle)
                .setView(view)
                .setPositiveButton(posBtnMsg,
                    DialogInterface.OnClickListener { dialog, id ->
                        var err = false
                        val name = nameInput.text.toString()
                        val type = typeInput.selectedItem.toString()
                        val growth = harvestInput.text

                        if (name.isEmpty()) {
                            err = true
                            Toast.makeText(
                                context,
                                "You did not enter a crop name.", Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (type == "") {
                            err = true
                            Toast.makeText(
                                context,
                                "You did not select a crop type.", Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (growth.isEmpty()) {
                            err = true
                            Toast.makeText(
                                context,
                                "You did not enter a growth time.", Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (waterHours.isEmpty()) {
                            err = true
                            Toast.makeText(
                                context,
                                "You did not enter a watering frequency.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (!err) {
                            if (mEditCrop != null) {
                                mEditCrop!!.name = name
                                mEditCrop!!.type = type
                                mEditCrop!!.growthTime = growth.toString().toInt()
                                mEditCrop!!.setNewWaterHours(waterHours)
                                listener?.onEditDialogAccept(mEditCrop!!)
                            } else {
                                listener?.onAddDialogAccept(Crop(name, type,
                                    growth.toString().toInt(), waterHours)
                                )
                            }
                        }
                    })
                .setNegativeButton(R.string.NegButt,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                        dialog.cancel()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAddCropDialogInteraction) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnAddCropDialogInteraction")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnAddCropDialogInteraction {
        fun onAddDialogAccept(newCrop: Crop)
        fun onEditDialogAccept(editCrop: Crop)
    }
}