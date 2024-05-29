package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
public class HojaSprites {
    public final int[] pixeles;
    private final int ancho;
    private final int alto;

    public HojaSprites(final String ruta, final int ancho, final int alto){
        this.ancho = ancho;
        this.alto = alto;

        this.pixeles = new int[ancho * alto];

        BufferedImage imagen = null;
        try {
            imagen = ImageIO.read(HojaSprites.class.getResource(ruta));
            imagen.getRGB(0,0,ancho, alto, pixeles, 0, ancho);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }
}
