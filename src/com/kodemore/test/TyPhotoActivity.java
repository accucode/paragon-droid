/*
  Copyright (c) 2005-2012 Wyatt Love

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */

package com.kodemore.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.kodemore.file.KmFilePath;
import com.kodemore.file.KmSharedFilePath;
import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmLinearLayout;

/**
 * I am used for temporary tests.
 */
public class TyPhotoActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private ImageView              _imageView;
    private KmSimpleIntentCallback _photoCallback;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _imageView = new ImageView(getContext());

        _photoCallback = newPhotoCallback();
        _photoCallback.register(ui());
    }

    //==================================================
    //= init :: callback
    //==================================================

    private KmSimpleIntentCallback newPhotoCallback()
    {
        return new KmSimpleIntentCallback()
        {
            @Override
            public Intent getRequest()
            {
                try
                {
                    KmFilePath file = getTempFile();
                    Uri uri = file.toUri();

                    Intent i;
                    i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    return i;
                }
                catch ( Exception ex )
                {
                    throw Kmu.toRuntime(ex);
                }
            }

            @Override
            public void handleOk(Intent data)
            {
                try
                {
                    CompressFormat format = Bitmap.CompressFormat.JPEG;
                    int quality = 50; // 0..100

                    ContentResolver resolver;
                    resolver = getContext().getContentResolver();

                    Uri inUri = getTempFile().toUri();
                    InputStream in = resolver.openInputStream(inUri);

                    File outPath = getOutputFile().getRealFile();
                    FileOutputStream out = new FileOutputStream(outPath);

                    Rect padding = null;

                    Options opts;
                    opts = new BitmapFactory.Options();
                    opts.inSampleSize = 3;

                    Bitmap bmp;
                    bmp = BitmapFactory.decodeStream(in, padding, opts);
                    bmp.compress(format, quality, out);
                    bmp.recycle();

                    updateImageView();

                    System.gc();
                }
                catch ( Exception ex )
                {
                    throw Kmu.toRuntime(ex);
                }
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmLinearLayout root;
        root = ui().newColumn();

        KmLinearLayout buttons;
        buttons = root.addEvenRow();
        buttons.addButton("Test", newTestAction());
        buttons.addButton("Clear", newClearAction());
        buttons.addButton("", newReadAction());
        buttons.addButton("Photo", _photoCallback);

        root.addView(_imageView);

        updateImageView();

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newTestAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleTest();
            }
        };
    }

    private KmAction newClearAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleClear();
            }
        };
    }

    private KmAction newReadAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleRead();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleTest()
    {
        updateImageView();
    }

    private void handleClear()
    {
        clearImageView();
    }

    private void handleRead()
    {
        String s = getTextFile().readString();
        toast("read: " + s);
    }

    //##################################################
    //# utility
    //##################################################

    //==================================================
    //= utility :: file
    //==================================================

    private KmFilePath getTextFile()
    {
        return getFile("value.txt");
    }

    private KmFilePath getTempFile()
    {
        return getFile("temp.bmp");
    }

    private KmFilePath getOutputFile()
    {
        return getFile("output.jpg");
    }

    private KmFilePath getFile(String name)
    {
        return new KmSharedFilePath(name);
    }

    //==================================================
    //= utility :: image
    //==================================================

    private void updateImageView()
    {
        clearImageView();

        KmFilePath file = getOutputFile();
        if ( file.exists() )
            setImageView(file);
    }

    private void clearImageView()
    {
        _imageView.setImageBitmap(null);
    }

    private void setImageView(KmFilePath file)
    {
        _imageView.setImageURI(file.toUri());
    }

}
