package com.example.demo;

import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
public class ImageController {
    /*Napisz kontroler REST ImageController, w którym znajdzie się metoda zawracająca obraz ze zmodyfikowaną jasnością.
     Metoda typu GET powinna przyjąć obraz w formacie base64 oraz liczbę całkowitą określającą jasność.
     Metoda powinna rozjaśnić obraz o podaną wartość i zwrócić go w formacie base64.*/
    @GetMapping("/imageBrightness")
    public String showBrightnessImage(@RequestParam String base64Image, @RequestParam int level) {
        if (base64Image.startsWith("data:image")) {
            base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
        }
        base64Image = base64Image.replace(" ", "+");
        base64Image = base64Image.replace("\n", "");
        String increasedBrightness = increaseBrigthnessReturnBase64(level,base64Image);
        String output = "<div>\n" +
                "  <img src=\"data:image/jpeg;base64," + increasedBrightness + "\" alt=\"Cursed cat\" />\n" +
                "</div>";
        return output;
    }

    private String increaseBrigthnessReturnBase64(int level, String base64Image) {
        BufferedImage image = increaseBrigthnessReturnBufferedImage(level, base64Image);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
        }
        return Base64.getEncoder().encodeToString(os.toByteArray());
    }
    /*
    Zadanie 7.
    Napisz kolejną, zbliżoną metodę, w której wyniku znajdzie się niezakodowany obraz.
    */
    private BufferedImage increaseBrigthnessReturnBufferedImage(int level, String base64Image) {
        byte[] bytes = Base64.getDecoder().decode(base64Image);

        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                int b = rgb & 0XFF;
                int g = (rgb & 0XFF00) >> 8;
                int r = (rgb & 0XFF0000) >> 16;

                int newB = clamp(b + level, 0, 255);
                int newG = clamp(g + level, 0, 255);
                int newR = clamp(r + level, 0, 255);

                image.setRGB(x, y, (newR << 16) + (newG << 8) + newB);
            }
        }
        return image;
    }

    private static int clamp(int value, int min, int max){
        if (value > max)
            return max;
        if (value<min)
            return min;
        return value;
    }
}
