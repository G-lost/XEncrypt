package com.xencrypt.headfile.xencrypt;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.R.attr.data;
import static android.R.attr.path;
import static android.R.attr.x;

import static com.xencrypt.headfile.xencrypt.R.id.image_decrypt;
import static com.xencrypt.headfile.xencrypt.R.id.image_encrypt;
import static com.xencrypt.headfile.xencrypt.R.id.video_decrypt;
import static com.xencrypt.headfile.xencrypt.R.id.video_encrypt;

public class MainActivity extends AppCompatActivity
{
    Button addFile;
    Button addKey;
    Button image_encrypt;
    Button image_decrypt;
    Button video_encrypt;
    Button video_decrypt;
    TextView choosedFile;
    String file_actPath = null;
    String key_actPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int findKey = 2;
        final int findFile = 1;

        addFile = (Button) findViewById(R.id.addfile);
        addKey = (Button) findViewById(R.id.addkey);
        image_encrypt = (Button) findViewById(R.id.image_encrypt);
        image_decrypt = (Button) findViewById(R.id.image_decrypt);
        video_encrypt = (Button) findViewById(R.id.video_encrypt);
        video_decrypt = (Button) findViewById(R.id.video_decrypt);
        choosedFile = (TextView) findViewById(R.id.choosedFile);

        addFile.setText(addFile.getText(), TextView.BufferType.EDITABLE);
        addKey.setText(addKey.getText(), TextView.BufferType.EDITABLE);

        addFile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Intent intent = new Intent(Intent.ACTION_PICK, null);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, findFile);
            }
        });
        addKey.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Intent intent = new Intent(Intent.ACTION_PICK, null);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "*/*");
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, findKey);
            }
        });


        image_encrypt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (file_actPath == null||key_actPath==null)
                {
                    Toast.makeText(myApplication.getContext(), "还没选择文件或密钥", Toast.LENGTH_LONG).show();
                    return;
                }
                try
                {
                    com.xencrypt.headfile.xencryptAlgorithm.ImgEncrypt.imgEncrypt(file_actPath,key_actPath);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                choosedFile.append("\nEncrypt done!");
            }
        });
        image_decrypt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (file_actPath == null||key_actPath==null)
                {
                    Toast.makeText(myApplication.getContext(), "还没选择文件或密钥", Toast.LENGTH_LONG).show();
                    return;
                }
                try
                {
                    com.xencrypt.headfile.xencryptAlgorithm.ImgDecrypt.imgDecrypt(file_actPath,key_actPath);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                choosedFile.append("\n" + "Decrypt done!");
            }
        });

        video_decrypt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (file_actPath == null||key_actPath==null)
                {
                    Toast.makeText(myApplication.getContext(), "还没选择文件或密钥", Toast.LENGTH_LONG).show();
                    return;
                }
                try
                {
                    com.xencrypt.headfile.xencryptAlgorithm.VideoDecrypt.videoDecrypt(file_actPath,key_actPath);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                choosedFile.append("\n" + "Decrypt done!");
            }
        });

        video_encrypt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (file_actPath == null||key_actPath==null)
                {
                    Toast.makeText(myApplication.getContext(), "还没选择文件或密钥", Toast.LENGTH_LONG).show();
                    return;
                } else
                {
                    try
                    {
                        com.xencrypt.headfile.xencryptAlgorithm.VideoEncrypt.videoEncrypt(file_actPath,key_actPath);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                choosedFile.append("\n" + "Encrypt done!");//加done
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1)
        {//是否选择，没选择就不会继续
            //            Uri originalUri = data.getData();
            //            String[] proj = {MediaStore.Images.Media.DATA};
            //            Cursor cursor = managedQuery( originalUri, proj, null, null, null );
            //            int column_index = cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
            //            cursor.moveToFirst();
            //            String path = cursor.getString( column_index );
            Uri originalUri = data.getData();
            file_actPath = GetPathFromUri4kitkat.getPath(myApplication.getContext(), originalUri);
            Toast.makeText(MainActivity.this, file_actPath, Toast.LENGTH_SHORT).show();
            choosedFile.setText("\nfile:" + file_actPath);
        }
        if (requestCode == 2)
        {
            Uri originalUri = data.getData();
            key_actPath = GetPathFromUri4kitkat.getPath(myApplication.getContext(), originalUri);
            Toast.makeText(MainActivity.this, key_actPath, Toast.LENGTH_SHORT).show();
            choosedFile.append("\nkey:" + key_actPath);
        }

    }
}
