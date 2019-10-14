package com.example.flipupward;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

//TODO compare flipped card numbers, if wrong also flip back all cards
//TODO put an animation listener that on end animation checks if the game needs to be reset or not, and if it does it calls resetgamestatemethod

public class CardView extends RelativeLayout {

    private boolean isFaceDown;

    private TextView mTextView;
    private ImageView mBackImageView;
    private ImageView mFaceImageView;
    private FrameLayout mCardFrontFrame;
    private int value;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;

    private static int flipCounter = 0;
    private static int flipTargetNum;
    private static int previousVal = 0;
    private static boolean reset = false;

    private static ArrayList<CardView> arrListCardView = new ArrayList<>();

    public CardView(Context context, int dimensions) {
        super(context);
        isFaceDown = true;
        value = NumGen.generate();
        if (flipTargetNum == 0){flipTargetNum = dimensions*dimensions;}
        //TODO put all created objects in an arraylist
        arrListCardView.add(this);

        inflateLayout(context);
    }

    private void inflateLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.card_layout, this);

        mTextView = findViewById(R.id.card_value);
        mTextView.setText(String.valueOf(value));
        mBackImageView = findViewById(R.id.cardback);
        mFaceImageView = findViewById(R.id.cardfront);
        mCardFrontFrame = findViewById(R.id.cardfrontframe);

        setupAnimations(context);

        this.setOnClickListener(onClick -> {
            if (this.isFaceDown){
                if (value<previousVal){
                    reset = true;
                    setCardsNonClickable();
                }
                flipCounter++;
                faceUp();
                checkGameState();
            }
        });

    }

    private void setupAnimations(Context context) {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.show_card_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.hide_card_animation);

        mSetRightOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (reset){
                    resetGameState();
                }
            }
        });

        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mBackImageView.setCameraDistance(scale);
        mFaceImageView.setCameraDistance(scale);
    }

    public void faceUp() {
        mSetRightOut.setTarget(mCardFrontFrame);
        mSetLeftIn.setTarget(mBackImageView);
        mSetRightOut.start();
        mSetLeftIn.start();
        isFaceDown = false;
    }

    public void faceDown() {
        mSetRightOut.setTarget(mBackImageView);
        mSetLeftIn.setTarget(mCardFrontFrame);
        mSetRightOut.start();
        mSetLeftIn.start();
        isFaceDown = true;
    }

    public static void checkGameState(){
        if (flipCounter==flipTargetNum){
            //TODO win
        }

    }

    public static void resetGameState(){
        for (int i=0;i<arrListCardView.size();i++){
            if (!arrListCardView.get(i).isFaceDown){
                arrListCardView.get(i).faceDown();
            }
        }
        reset = false;
    }

    public static void setCardsClickable(){
        for (int i = 0;i<arrListCardView.size();i++)
            arrListCardView.get(i).setClickable(true);
    }

    public static void setCardsNonClickable(){
        for (int i = 0;i<arrListCardView.size();i++)
            arrListCardView.get(i).setClickable(false);
    }


}
