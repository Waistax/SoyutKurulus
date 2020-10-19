/**
 * başaşağıderebeyi.soyutkuruluş.SoyutKuruluş.java
 * 0.1 / 18 Eki 2020 / 09:16:34
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş;

import başaşağıderebeyi.awtkütüphanesi.*;
import başaşağıderebeyi.matematik.*;
import başaşağıderebeyi.motor.*;
import başaşağıderebeyi.soyutkuruluş.dünya.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;
import başaşağıderebeyi.soyutkuruluş.yaratıcı.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class SoyutKuruluş implements Uygulama {
	public static final String SÜRÜM = "0.5";
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
			final Vektör2 köşe = dünya.köşeler.get(rastgele.nextInt(dünya.köşeler.size()));
			final List<Kenar> bağlıKenarlar = new ArrayList<>();
			for (final Kenar kenar : dünya.kenarlar)
				if (Dünya.aynı(kenar.başlangıç, köşe) || Dünya.aynı(kenar.bitiş, köşe))
					bağlıKenarlar.add(kenar);
			final Kenar kenar = bağlıKenarlar.get(rastgele.nextInt(bağlıKenarlar.size()));
			dünya.şehirOluştur(ulus, köşe);
			dünya.yolOluştur(ulus, kenar);
		}
		if (dünya != null)
			dünyaArayüzü.kare(GÖRSELLEŞTİRİCİ.girdi, GÖRSELLEŞTİRİCİ.çizer, dünya);
		GÖRSELLEŞTİRİCİ.çizer.drawString("Kare: " + Motor.kareOranı, 10, 20);
	}

	@Override
	public void saniye() {
	}
}
