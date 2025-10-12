package com.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Classe utilitária para gerar códigos QR a partir de texto.
 */
public class QrCodeGenerator {

    /**
     * Gera um ficheiro PNG com o QR Code correspondente ao texto fornecido.
     *
     * @param texto Conteúdo a codificar no QR Code.
     * @param caminhoFicheiro Caminho onde o ficheiro PNG será guardado.
     */
    public static void gerarQRCode(String texto, String caminhoFicheiro) {
        try {
            int largura = 250;
            int altura = 250;
            String formato = "png";

            BitMatrix matriz = new MultiFormatWriter()
                    .encode(texto, BarcodeFormat.QR_CODE, largura, altura);

            Path caminho = FileSystems.getDefault().getPath(caminhoFicheiro);
            MatrixToImageWriter.writeToPath(matriz, formato, caminho);

            System.out.println("QR Code gerado com sucesso: " + caminhoFicheiro);
        } catch (Exception e) {
            System.err.println("Erro ao gerar QR Code: " + e.getMessage());
        }
    }
}

