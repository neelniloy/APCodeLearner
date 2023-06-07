
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CustomAlertDialog(
    val icon: Int,
    val title: String,
    val body: String,
    val posBtnText: String,
    val negBtnText: String,
    val posBtnCallback: () -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(title)
        builder.setIcon(icon)
        builder.setMessage(body)
        builder.setPositiveButton(posBtnText) { dialog, which ->
            posBtnCallback()
        }
        builder.setNegativeButton(negBtnText, null)
        return builder.create()
    }
}