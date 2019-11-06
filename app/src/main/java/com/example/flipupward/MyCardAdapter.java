package com.example.flipupward;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.ViewHolder> {

    Context context;
    List<RandomNumberModel> list;


    public MyCardAdapter(Context context, List<RandomNumberModel> list){
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.BindData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }

        public void BindData(int position) {
            TextView mTextView = view.findViewById(R.id.card_value);
            ImageView mBackImageView = view.findViewById(R.id.cardback);
            ImageView mFaceImageView = view.findViewById(R.id.cardfront);
            FrameLayout mCardFrontFrame = view.findViewById(R.id.cardfrontframe);

            mTextView.setText(String.valueOf(list.get(position).getNum()));

            AnimatorSet mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.show_card_animation);
            AnimatorSet mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.hide_card_animation);

            view.setOnClickListener(onClick -> {
                if (!list.get(position).isSelecter()) {
                    if (isLessThen(position)) {
                        setCardsNonClickable();
                        gameOver = true;
                        mSetLeftIn.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (gameOver) {
                                    resetGameState();
                                } else if (resetting) {
                                    setCardsClickable();
                                    resetting = false;
                                    mSetLeftIn.removeAllListeners();
                                }
                            }
                        });
                        faceUp();
                    } else {
                        flipCounter++;
                        previousVal = value;
                        faceUp();

                        if (flipCounter == flipTargetNum) {
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

        public boolean isLessThen(int position) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelecter() && list.get(i).getNum() > list.get(position).getNum() && i != position) {
                    return true;
                }
            }
            return false;
        }

        private void setupAnimations(Context context, ImageView mBackImageView, ImageView mFaceImageView) {

            int distance = 8000;
            float scale = context.getResources().getDisplayMetrics().density * distance;
            mBackImageView.setCameraDistance(scale);
            mFaceImageView.setCameraDistance(scale);
        }

        public void faceUp(AnimatorSet mSetRightOut, AnimatorSet mSetLeftIn, ImageView mBackImageView, FrameLayout mCardFrontFrame, int position) {
            mSetRightOut.setTarget(mCardFrontFrame);
            mSetLeftIn.setTarget(mBackImageView);
            mSetRightOut.start();
            mSetLeftIn.start();
        }

        public void faceDown(AnimatorSet mSetRightOut, AnimatorSet mSetLeftIn, ImageView mBackImageView, FrameLayout mCardFrontFrame, int position) {
            mSetRightOut.setTarget(mBackImageView);
            mSetLeftIn.setTarget(mCardFrontFrame);
            mSetRightOut.start();
            mSetLeftIn.start();
        }
    }
}
