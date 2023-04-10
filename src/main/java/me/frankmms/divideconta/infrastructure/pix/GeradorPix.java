package me.frankmms.divideconta.infrastructure.pix;

import com.github.mattnicee7.pixqrcode.exception.InvalidValueFormatException;
import com.github.mattnicee7.pixqrcode.pixqrcode.PixQRCode;
import com.github.mattnicee7.pixqrcode.pixqrcode.PixQRCodeBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.AllArgsConstructor;
import me.frankmms.divideconta.domain.GeradorDadosPagamento;
import me.frankmms.divideconta.domain.model.FormaPagamento;
import me.frankmms.divideconta.domain.model.Dinheiro;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

@AllArgsConstructor
public class GeradorPix implements GeradorDadosPagamento {

    PixConfig config;

    @Override
    public Map<String, String> gerar(FormaPagamento forma, Dinheiro valor) {
        try {
            final PixQRCode myPixWithValue = new PixQRCodeBuilder()
                    .receiverFullName(config.getNomeRecebedor())
                    .pixKey(config.getChave())
                    .receiverCity(config.getCidadeRecebedor())
                    .transactionIdentifier("DIVIDECONTA")
                    .withValue(true)
                    .value(valor.doubleValue())
                    .build();

            String codigoCopiaCola = myPixWithValue.getAsText();

            return Map.of(
                    "TIPO_CHAVE", config.getTipoChave(),
                    "CHAVE", config.getChave(),
                    "CODIGO_COPIA_COLA", codigoCopiaCola,
                    "QRCODE", generateQRCode(codigoCopiaCola)
            );
        } catch (InvalidValueFormatException e) {
            throw new IllegalStateException("Não foi possível gerar o código PIX", e);
        }
    }

    protected String generateQRCode(String code) {
        var output = new ByteArrayOutputStream();
        generateQRCode(code, output);
        var encodedString = Base64.getEncoder().encodeToString(output.toByteArray());

        return encodedString;
    }

    protected void generateQRCode(String code, OutputStream output) {
        final var hintsMap = new EnumMap<>(EncodeHintType.class);
        hintsMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintsMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        final int sizeInPixels = 300;

        final var writer = new QRCodeWriter();
        try {
            final var bitMatrix = writer.encode(code, BarcodeFormat.QR_CODE, sizeInPixels, sizeInPixels, hintsMap);
            final var image = new BufferedImage(sizeInPixels, sizeInPixels, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < sizeInPixels; y++) {
                for (int x = 0; x < sizeInPixels; x++) {
                    final var isBlack = bitMatrix.get(x, y);
                    final int color = isBlack ? 0 : 0xFFFFFF; //black or white
                    image.setRGB(x, y, color);
                }
            }

            ImageIO.write(image, "png", output);
        } catch (IOException | WriterException e) {
            throw new RuntimeException("Unable to generate QRCode", e);
        }
    }

}
