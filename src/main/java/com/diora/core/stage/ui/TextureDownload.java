package com.diora.core.stage.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.StreamUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TextureDownload {

    public TextureDownload(final String url,final FinishedDownload finishedDownload) {
        if(!url.equals(""))
            new Thread(new Runnable() {
                /** Downloads the content of the specified url to the array. The array has to be big enough. */
                private int download (byte[] out, String url) {
                    InputStream in = null;
                    try {
                        HttpURLConnection conn = null;
                        conn = (HttpURLConnection)new URL(url).openConnection();
                        conn.setDoInput(true);
                        conn.setDoOutput(false);
                        conn.setUseCaches(true);
                        conn.connect();
                        in = conn.getInputStream();
                        int readBytes = 0;
                        while (true) {
                            int length = in.read(out, readBytes, out.length - readBytes);
                            if (length == -1) break;
                            readBytes += length;
                        }
                        return readBytes;
                    } catch (Exception ex) {
                        return 0;
                    } finally {
                        StreamUtils.closeQuietly(in);
                    }
                }

                @Override
                public void run () {
                    byte[] bytes = new byte[200 * 1024]; // assuming the content is not bigger than 200kb.
                    int numBytes = download(bytes, url);
                    if (numBytes != 0) {
                        // load the pixmap, make it a power of two if necessary (not needed for GL ES 2.0!)
                        Pixmap pixmap = new Pixmap(bytes, 0, numBytes);
                        final int originalWidth = pixmap.getWidth();
                        final int originalHeight = pixmap.getHeight();
                        int width = MathUtils.nextPowerOfTwo(pixmap.getWidth());
                        int height = MathUtils.nextPowerOfTwo(pixmap.getHeight());

                        final Pixmap roundPixmap = roundPixmap(pixmap);

                        final Pixmap potPixmap = new Pixmap(width, height, pixmap.getFormat());
                        potPixmap.drawPixmap(roundPixmap, 0, 0, 0, 0, roundPixmap.getWidth(), roundPixmap.getHeight());
                        pixmap.dispose();
                        roundPixmap.dispose();
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run () {
                                finishedDownload.finished(new TextureRegion(new Texture(potPixmap), 0, 0, originalWidth, originalHeight));
                            }
                        });
                    }
                }
            }).start();

    }

/*    public void pixmapMask(Pixmap pixmap, Pixmap mask, Pixmap result, boolean invertMaskAlpha){
        int pixmapWidth = pixmap.getWidth();
        int pixmapHeight = pixmap.getHeight();
        Color pixelColor = new Color();
        Color maskPixelColor = new Color();

        Pixmap.Blending blending = Pixmap.getBlending();
        Pixmap.setBlending(Pixmap.Blending.None);
        for (int x=0; x<pixmapWidth; x++){
            for (int y=0; y<pixmapHeight; y++){
                Color.rgba8888ToColor(pixelColor, pixmap.getPixel(x, y));                           // get pixel color
                Color.rgba8888ToColor(maskPixelColor, mask.getPixel(x, y));                         // get mask color

                maskPixelColor.a = (invertMaskAlpha) ? 1.0f-maskPixelColor.a : maskPixelColor.a;    // IF invert mask
                pixelColor.a = pixelColor.a * maskPixelColor.a;                                     // multiply pixel alpha * mask alpha
                result.setColor(pixelColor);
                result.drawPixel(x, y);
            }
        }
        Pixmap.setBlending(blending);
    }*/

    private Pixmap roundPixmap(Pixmap pixmap) {
        int width = pixmap.getWidth();
        int height = pixmap.getHeight();
        Pixmap round = new Pixmap(pixmap.getWidth(),pixmap.getHeight(),Pixmap.Format.RGBA8888);
        if(width != height) {
            Gdx.app.log("error", "Cannot create round image if width != height");
            round.dispose();
            return pixmap;
        }
        double radius = width/2.0;
        for(int y=0;y<height;y++) {
            for(int x=0;x<width;x++) {
                //check if pixel is outside circle. Set pixel to transparant;
                double dist_x = (radius - x);
                double dist_y = radius - y;
                double dist = Math.sqrt((dist_x*dist_x) + (dist_y*dist_y));
                if(dist < radius) {
                    round.drawPixel(x, y,pixmap.getPixel(x, y));
                }
                else
                    round.drawPixel(x, y, 0);
            }
        }
        Gdx.app.log("info", "pixmal rounded!");
        return round;
    }
}