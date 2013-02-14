package s171183.android1.hioa;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

@SuppressLint({ "NewApi", "ValidFragment" })
public class RulesDialog extends DialogFragment {
	

	
	public RulesDialog(){
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		String message = getString(R.string.rules);
		String title = getString(R.string.rules_title);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title);
		builder.setMessage(message).
			setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
		
		return builder.create();
	}
}
