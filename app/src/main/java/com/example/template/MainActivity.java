package com.example.template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {
    Button AddTextBtn;
    String newbox;
    ImageView font,textSize,textColor,editText,doneBtn,incFont,decFont,sizeChangeDone,moveBtn,fontChooseDoneBtn;
    int fontsize = 14;
    boolean isMovable = false;
    int num=1;
    TextView f1,f2,f3,f4;
    int defaultColor;
    LinearLayout fontBox,sizeChangeBox,allTool;
    RelativeLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f1=findViewById(R.id.f1);
        f2=findViewById(R.id.f2);
        f3=findViewById(R.id.f3);
        f4=findViewById(R.id.f4);
        fontChooseDoneBtn=findViewById(R.id.fontChooseDone);
        AddTextBtn = findViewById(R.id.AddTextbtn);
        AddTextBtn.setBackground(getResources().getDrawable(R.drawable.add_text_btn));
        mLinearLayout=findViewById(R.id.box);
        font=findViewById(R.id.fontBtn);
        textSize=findViewById(R.id.textSizeBtn);
        textColor=findViewById(R.id.fontColorBtn);
        editText=findViewById(R.id.editTextBtn);
        doneBtn=findViewById(R.id.doneBtn);
        incFont=findViewById(R.id.sizeInc);
        decFont=findViewById(R.id.sizeDec);
        moveBtn = findViewById(R.id.moveBtn);
        fontBox=findViewById(R.id.fontBox);
        allTool = findViewById(R.id.allTool);
        sizeChangeBox  =findViewById(R.id.sizeChangeBox);
        sizeChangeDone=findViewById(R.id.sizechangeDone);
        newbox="AddTextView"+num;
        AddTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText newbox = new EditText(MainActivity.this);
                newbox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                newbox.setMinEms(6);
                newbox.setId(num);
                newbox.setHint("Add Text");
                newbox.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                newbox.setInputType(InputType.TYPE_NULL);
                newbox.setPadding(2,5,2,5);
                newbox.setTextSize(14);
                newbox.setCursorVisible(true);
                newbox.setImeOptions(EditorInfo.IME_ACTION_DONE);
                newbox.setBackground(getResources().getDrawable(R.drawable.selected_box));
                Typeface typeface = getResources().getFont(R.font.teko);
                newbox.setTypeface(typeface);

                newbox.setTextColor(getResources().getColor(R.color.black));
                mLinearLayout.addView(newbox);
                num=num+1;
                Log.d("MainActivity", "IDCHANGED");
                click(newbox);

            }
        });

    }

    private void closekeyboard() {
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    public void restrictWriting(int id){
        EditText newbox = findViewById(id);
        newbox.setInputType(InputType.TYPE_NULL);
        newbox.setCursorVisible(false);
    }

    public void done(View v){
        isMovable=false;
        allTool.setVisibility(View.GONE);
        AddTextBtn.setVisibility(View.VISIBLE);
    }


    public void click(View v){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "working", Toast.LENGTH_SHORT).show();
                int id=select(v);
                isMovable=false;

                moveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isMovable=true;
                        if(isMovable){
                            drag(v);
                        }

                    }
                });
                mLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closekeyboard();
                        restrictWriting(id);
                    }
                });


                doneBtn.setOnClickListener(new View.OnClickListener() {
                    public void deselect(int id) {
                        EditText box = findViewById(id);
                        box.setBackground(getResources().getDrawable(R.drawable.selected_box));
                        box.setInputType(InputType.TYPE_NULL);
                    }
                    public void onClick(View view) {
                        done(view);
                        deselect(id);
                        Log.d("isMovable", String.valueOf(isMovable));
                    }

                });

                editText.setOnClickListener(new View.OnClickListener() {
                    public void edit(int id){
                        EditText box = findViewById(id);
                        box.setInputType(InputType.TYPE_CLASS_TEXT);
                        InputMethodManager immm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        immm.showSoftInput(box,0);

                    }
                    public void onClick(View view) {
                        edit(id);
                        Toast.makeText(MainActivity.this, "editable", Toast.LENGTH_SHORT).show();
                    }
                });

                textColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       allTool.setVisibility(View.VISIBLE);
                        openColorPicker(id);
                    }
                });
                textSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        allTool.setVisibility(View.GONE);
                        sizeChangeBox.setVisibility(View.VISIBLE);
                    }
                });
                incFont.setOnClickListener(new View.OnClickListener() {
                    public void sizeinc(int id){
                        EditText box = findViewById(id);
                        fontsize = fontsize+3;
                        box.setTextSize(fontsize);
                    }
                    @Override
                    public void onClick(View view) {
                        if(fontsize<=30)
                            sizeinc(id);
                    }
                });
                decFont.setOnClickListener(new View.OnClickListener() {
                    public void sizedec(int id){
                        EditText box = findViewById(id);
                        fontsize = fontsize-3;
                        box.setTextSize(fontsize);
                    }
                    @Override
                    public void onClick(View view) {
                        if(fontsize>=8)
                            sizedec(id);
                    }
                });
                sizeChangeDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        allTool.setVisibility(View.VISIBLE);
                        sizeChangeBox.setVisibility(View.GONE);
                        fontsize =12;
                    }
                });

                font.setOnClickListener(new View.OnClickListener() {
                    public void changeFont(int id,Typeface t){
                        EditText box = findViewById(id);
                        box.setTypeface(t);
                    }
                    @Override
                    public void onClick(View view) {
                        fontBox.setVisibility(View.VISIBLE);
                        sizeChangeBox.setVisibility(View.GONE);
                        allTool.setVisibility(View.GONE);

                        f1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Typeface t1 = f1.getTypeface();
                                changeFont(id,t1);
                            }
                        });
                        f2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Typeface t2 = f2.getTypeface();
                                changeFont(id,t2);
                            }
                        });
                        f3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Typeface t3 = f3.getTypeface();
                                changeFont(id,t3);
                            }
                        });
                        f4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Typeface t4 = f4.getTypeface();
                                changeFont(id,t4);
                            }
                        });
                        fontChooseDoneBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fontBox.setVisibility(View.GONE);
                                allTool.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }

        });
    }

    public void openColorPicker(int id){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor=color;
                EditText newbox = findViewById(id);
                newbox.setTextColor(defaultColor);
            }
        });
        ambilWarnaDialog.show();
    }
    public int select(View v) {
        v.setBackground(getResources().getDrawable(R.drawable.add_text_box));
        AddTextBtn.setVisibility(View.GONE);
        allTool.setVisibility(View.VISIBLE);
        return (v.getId());
    }

    public void drag(View v){
        Log.d("isMovable", String.valueOf(isMovable));
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                float xDown=v.getWidth()/2,yDown=v.getHeight()/2;

                switch (event.getActionMasked()){

                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown= event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float movedX,movedY;
                        movedX= event.getX();
                        movedY =event.getY();

                        float distanceX=movedX-xDown;
                        float distanceY=movedY-yDown;

                        v.setX(v.getX()+distanceX);
                        v.setY(v.getY()+distanceY);
                        break;
                }

                return false;
            }
        });
    }

}

