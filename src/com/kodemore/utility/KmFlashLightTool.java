package com.kodemore.utility;

import java.util.ArrayList;
import java.util.List;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

public class KmFlashLightTool
{
    //##################################################
    //# static
    //##################################################

    public static KmFlashLightTool instance = new KmFlashLightTool();

    //##################################################
    //# variables
    //##################################################

    private Camera                 _camera;

    //##################################################
    //# constructors
    //##################################################

    private KmFlashLightTool()
    {
        // private
    }

    //##################################################
    //# utility
    //##################################################

    private void startCamera()
    {
        if ( cameraOpen() )
            return;

        _camera = Camera.open();
    }

    private void stopCamera()
    {
        if ( !cameraOpen() )
            return;

        _camera.release();
        _camera = null;
    }

    private boolean cameraOpen()
    {
        return _camera != null;
    }

    //##################################################
    //# convenience
    //##################################################

    public boolean isLightOn()
    {
        if ( !cameraOpen() )
            return false;

        Parameters p;
        p = _camera.getParameters();
        return p.getFlashMode().equals(p.FLASH_MODE_TORCH);
    }

    public void start()
    {
        startCamera();
    }

    public void stop()
    {
        stopCamera();
    }

    public void turnOnLight()
    {
        startCamera();

        if ( !cameraOpen() )
            return;

        Parameters p;
        p = _camera.getParameters();
        p.setFlashMode(p.FLASH_MODE_TORCH);
        _camera.setParameters(p);
    }

    public void turnOffLight()
    {
        if ( !cameraOpen() )
            return;

        Parameters p;
        p = _camera.getParameters();
        p.setFlashMode(p.FLASH_MODE_OFF);
        _camera.setParameters(p);

        stopCamera();
    }

    public void toggleLight()
    {
        if ( isLightOn() )
            turnOffLight();
        else
            turnOnLight();
    }

    public boolean hasFlashLight()
    {
        startCamera();

        if ( !cameraOpen() )
            return false;

        List<String> v;
        v = new ArrayList<String>();
        v = _camera.getParameters().getSupportedFlashModes();

        stopCamera();

        if ( v == null )
            return false;

        return v.contains(Parameters.FLASH_MODE_TORCH);
    }
}
