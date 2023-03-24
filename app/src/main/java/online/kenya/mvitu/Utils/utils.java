package online.kenya.mvitu.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

import online.kenya.mvitu.R;
import online.kenya.mvitu.constants.FirebaseInit;
import online.kenya.mvitu.models.GoodType;

import java.util.List;
import java.util.Random;

public class utils {
    public static String generateCode() {
    // Create a Random object to generate random numbers
    Random random = new Random();

    // Generate a 7-character alphanumeric code
    String code = "";
    for (int i = 0; i < 7; i++) {
        // Generate a random number between 0 and 35
        int randomInt = random.nextInt(36);

        // Convert the random number to a character
        // 0-9 are represented by '0' to '9'
        // 10-35 are represented by 'A' to 'Z'
        char c;
        if (randomInt < 10) {
            c = (char)('0' + randomInt);
        } else {
            c = (char)('A' + (randomInt - 10));
        }

        // Add the character to the code
        code += c;
    }

    return code;
}

    public static Dialog createDialog(Context context, int contentView){
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(contentView);


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations= R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        return dialog;

    }
    public static void shareCode(String code, List<GoodType> goodTypeList, Activity activity) {
        /*Create an ACTION_SEND Intent*/
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*This will be the actual content you wish you share.*/
        String shareBody= "You have received, ";
        for (GoodType goodType:goodTypeList)
            shareBody+= " "+goodType.getGoodName()+" "+goodType.getGoodVariantName()+" "+goodType.getNumberInCart()+" items, ";
        shareBody += "from "+ FirebaseInit.mAuth.getCurrentUser().getPhoneNumber()+ ". Withdrawal code is "+code;
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        /*Fire!*/
        activity.startActivity(Intent.createChooser(intent, "which app do you want to share using"));
    }


}
