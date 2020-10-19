/**
 * başaşağıderebeyi.soyutkuruluş.SoyutKuruluş.java
 * 0.1 / 18 Eki 2020 / 09:16:34
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş;

import başaşağıderebeyi.awtkütüphanesi.*;
import başaşağıderebeyi.motor.*;
import başaşağıderebeyi.soyutkuruluş.dünya.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;
import başaşağıderebeyi.soyutkuruluş.yaratıcı.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class SoyutKuruluş implements Uygulama {
	public static final String SÜRÜM = "0.6";
	public static final SoyutKuruluş UYGULAMA = new SoyutKuruluş();
	public static final AWTGörselleştirici GÖRSELLEŞTİRİCİ = new AWTGörselleştirici();
	
	public static void main(final String[] args) {
		Motor.uygulama = UYGULAMA;
		Motor.görselleştirici = GÖRSELLEŞTİRİCİ;
		GÖRSELLEŞTİRİCİ.başlık = "Soyut Kuruluş srm. " + SÜRÜM;
		GÖRSELLEŞTİRİCİ.boyut.yaz(16.0F * 80.0F, 9.0F * 80.0F);
		GÖRSELLEŞTİRİCİ.arkaplanRengi = new Color(0.1F, 0.1F, 0.2F, 1.0F);
		Motor.başla();
	}
	
	public Yaratıcı yaratıcı;
	public Dünya dünya;
	public DünyaArayüzü dünyaArayüzü;
	
	@Override
	public void yükle() {
		yaratıcı = new RastgeleYaratıcı();
		dünyaArayüzü = new DünyaArayüzü();
	}

	@Override
	public void kaydet() {
	}

	@Override
	public void kare() {
		if (GÖRSELLEŞTİRİCİ.girdi.tuşBasıldı[KeyEvent.VK_Y]) {
			dünya = yaratıcı.yarat();
			final Random rastgele = new Random();
			final Ulus ulus = new Ulus(dünya, new Color(1.0F, 0.2F, 0.2F, 1.0F));
			final Köşe köşe = dünya.köşeler.get(rastgele.nextInt(dünya.köşeler.size()));
			final List<Kenar> kenarlar = new ArrayList<>(köşe.kenarlar.keySet());
			dünya.şehirOluştur(ulus, köşe);
			dünya.yolOluştur(ulus, kenarlar.get(rastgele.nextInt(kenarlar.size())));
		}
		if (dünya != null) {
			dünyaArayüzü.kare(GÖRSELLEŞTİRİCİ.girdi, GÖRSELLEŞTİRİCİ.çizer, dünya);
			final Ulus ulus = dünya.uluslar.get(0);
			for (int i = 0; i < Kaynak.DEĞERLER.length; i++) {
				GÖRSELLEŞTİRİCİ.çizer.setColor(Kaynak.DEĞERLER[i].renk);
				GÖRSELLEŞTİRİCİ.çizer.drawString(Kaynak.DEĞERLER[i] + ": " + ulus.envanter[i], 10, 50 + i * 20);
			}
		}
		GÖRSELLEŞTİRİCİ.çizer.setFont(new Font("Verdana", Font.ITALIC, 16));
		GÖRSELLEŞTİRİCİ.çizer.setColor(Color.WHITE);
		GÖRSELLEŞTİRİCİ.çizer.drawString("Kare: " + Motor.kareOranı, 10, 20);
	}

	@Override
	public void saniye() {
	}
}
