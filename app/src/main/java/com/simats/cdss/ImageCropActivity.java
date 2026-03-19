package com.simats.cdss;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ImageCropActivity extends AppCompatActivity {

    private ImageView ivCropImage;
    private Bitmap originalBitmap;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    private float startX, startY;
    private ScaleGestureDetector scaleDetector;
    private float currentScale = 1f;
    private float minScale = 0.5f;
    private float maxScale = 5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);

        ivCropImage = findViewById(R.id.iv_crop_image);

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        // Load image from intent URI
        String uriString = getIntent().getStringExtra("image_uri");
        if (uriString != null) {
            try {
                Uri imageUri = Uri.parse(uriString);
                InputStream in = getContentResolver().openInputStream(imageUri);
                originalBitmap = BitmapFactory.decodeStream(in);
                if (in != null) in.close();

                if (originalBitmap != null) {
                    ivCropImage.setImageBitmap(originalBitmap);
                    ivCropImage.post(() -> centerImage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } else {
            Toast.makeText(this, "No image provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        scaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        ivCropImage.setOnTouchListener((v, event) -> {
            scaleDetector.onTouchEvent(event);

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    savedMatrix.set(matrix);
                    startX = event.getX();
                    startY = event.getY();
                    mode = DRAG;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = ZOOM;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == DRAG) {
                        matrix.set(savedMatrix);
                        float dx = event.getX() - startX;
                        float dy = event.getY() - startY;
                        matrix.postTranslate(dx, dy);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    mode = NONE;
                    break;
            }

            ivCropImage.setImageMatrix(matrix);
            return true;
        });

        // Save cropped/adjusted image
        findViewById(R.id.btn_save_crop).setOnClickListener(v -> saveCroppedImage());
    }

    private void centerImage() {
        if (originalBitmap == null) return;
        float viewW = ivCropImage.getWidth();
        float viewH = ivCropImage.getHeight();
        float bmpW = originalBitmap.getWidth();
        float bmpH = originalBitmap.getHeight();

        float scale = Math.min(viewW / bmpW, viewH / bmpH);
        currentScale = scale;

        matrix.reset();
        matrix.postScale(scale, scale);
        matrix.postTranslate((viewW - bmpW * scale) / 2f, (viewH - bmpH * scale) / 2f);
        ivCropImage.setImageMatrix(matrix);
    }

    private void saveCroppedImage() {
        try {
            // Capture what's visible in the ImageView
            int w = ivCropImage.getWidth();
            int h = ivCropImage.getHeight();

            // Create a square crop from center
            int size = Math.min(w, h);
            int cropX = (w - size) / 2;
            int cropY = (h - size) / 2;

            Bitmap viewBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(viewBitmap);
            ivCropImage.draw(canvas);

            // Crop to square
            Bitmap croppedBitmap = Bitmap.createBitmap(viewBitmap, cropX, cropY, size, size);

            // Scale down to a reasonable profile size (256x256)
            Bitmap finalBitmap = Bitmap.createScaledBitmap(croppedBitmap, 256, 256, true);

            // Save to internal storage
            File file = new File(getFilesDir(), "profile_image.jpg");
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            // Clean up
            if (croppedBitmap != viewBitmap) croppedBitmap.recycle();
            viewBitmap.recycle();

            Uri savedUri = Uri.fromFile(file);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("cropped_uri", savedUri.toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            float newScale = currentScale * scaleFactor;

            if (newScale >= minScale && newScale <= maxScale) {
                currentScale = newScale;
                matrix.postScale(scaleFactor, scaleFactor,
                        detector.getFocusX(), detector.getFocusY());
                ivCropImage.setImageMatrix(matrix);
            }
            return true;
        }
    }
}
