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

public class CardView extends RelativeLayout {

    //TODO every value needs to be reset, forgot, arraylists cleared, etc at start of new game

    private boolean isFaceDown;

    private TextView mTextView;
    private ImageView mBackImageView;
    private ImageView mFaceImageView;
    private FrameLayout mCardFrontFrame;
    private int value;
    private OnClickListener listener;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;

    private static int flipCounter = 0;
    private static int flipTargetNum;
    private static int previousVal = 0;
    private static boolean gameOver = false;
    private static boolean resetting = false;

    public static ArrayList<CardView> arrListCardView = new ArrayList<>();

    public CardView(Context context, OnClickListener listener) {
        super(context);
        isFaceDown = true;
        this.listener = listener;
        value = NumGen.generate();
        arrListCardView.add(this);
        flipTargetNum = arrListCardView.size();


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
            if (isFaceDown){
                if (value<previousVal){
                    setCardsNonClickable();
                    gameOver = true;
                    mSetLeftIn.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (gameOver){
                                resetGameState();
                            }
                            else if (resetting){
                                setCardsClickable();
                                resetting = false;
                                mSetLeftIn.removeAllListeners();
                            }
                        }
                    });
                    faceUp();
                }else{
                    flipCounter++;
                    previousVal = value;
                    faceUp();

                    if (flipCounter==flipTargetNum){
                        mSetLeftIn.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                listener.onClick(null);
                                mSetLeftIn.removeAllListeners();
                            }
                        });
                    }
                }
            }
        });

    }

    private void setupAnimations(Context context) {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.show_card_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.hide_card_animation);

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

    public static void resetGameState(){
        flipCounter = 0;
        previousVal = 0;
        for (int i=0;i<arrListCardView.size();i++){
            if (!arrListCardView.get(i).isFaceDown){
                arrListCardView.get(i).faceDown();
            }
        }
        gameOver = false;
        resetting = true;
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