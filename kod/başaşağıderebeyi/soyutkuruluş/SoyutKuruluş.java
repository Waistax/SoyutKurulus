/**
 * başaşağıderebeyi.soyutkuruluş.SoyutKuruluş.java
 * 0.1 / 18 Eki 2020 / 09:16:34
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş;

import static başaşağıderebeyi.soyutkuruluş.dünya.Kaynak.*;

import başaşağıderebeyi.awtkütüphanesi.*;
import başaşağıderebeyi.motor.*;
import başaşağıderebeyi.soyutkuruluş.dünya.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;
import başaşağıderebeyi.soyutkuruluş.yaratıcı.*;

import java.awt.*;
import java.awt.event.*;

public class SoyutKuruluş implements Uygulama {
	public static final String SÜRÜM = "0.10";
	public static final SoyutKuruluş UYGULAMA = new SoyutKuruluş();
	public static final AWTGörselleştirici GÖRSELLEŞTİRİCİ = new AWTGörselleştirici();
	public static final Süreç[] SÜREÇLER = {
			new Süreç(),
			new Süreç(),
			new Süreç()
	};
	
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
	public int aktifSüreç;
	
	@Override
	public void yükle() {
		yaratıcı = new RastgeleYaratıcı();
		dünyaArayüzü = new DünyaArayüzü();
		aktifSüreç = -1;
	}

	@Override
	public void kaydet() {
	}

	@Override
	public void kare() {
		if (GÖRSELLEŞTİRİCİ.girdi.tuşBasıldı[KeyEvent.VK_Y])
			dünya = yaratıcı.yarat();
		if (dünya != null) {
			dünyaArayüzü.kare(GÖRSELLEŞTİRİCİ.girdi, GÖRSELLEŞTİRİCİ.çizer, dünya);
			final Ulus ulus = dünya.uluslar.get(0);
			GÖRSELLEŞTİRİCİ.çizer.setColor(Color.WHITE);
			GÖRSELLEŞTİRİCİ.çizer.drawString("GÜMÜŞ: " + ulus.gümüş, 10, 180);
			for (int i = 0; i < DEĞERLER.length; i++) {
				GÖRSELLEŞTİRİCİ.çizer.setColor(DEĞERLER[i].renk);
				GÖRSELLEŞTİRİCİ.çizer.drawString(DEĞERLER[i] + ": " + ulus.envanter[i], 10, 200 + i * 20);
			}
		}
		GÖRSELLEŞTİRİCİ.çizer.setFont(new Font("Verdana", Font.ITALIC, 16));
		GÖRSELLEŞTİRİCİ.çizer.setColor(Color.WHITE);
		GÖRSELLEŞTİRİCİ.çizer.drawString("Kare: " + Motor.kareOranı, 10, 20);
		for (int i = 0; i < SÜREÇLER.length; i++)
			GÖRSELLEŞTİRİCİ.çizer.drawString("Sür" + i + ": " + SÜREÇLER[i].ortalamayıAl(), 10, 40 + i * 20);
	}

	@Override
	public void saniye() {
		for (final Süreç süreç : SÜREÇLER)
			süreç.hesapla();
		System.gc();
	}
	
	public void süreceGeç(int süreç) {
		if (aktifSüreç >= 0)
			SÜREÇLER[aktifSüreç].dur();
		aktifSüreç = süreç;
		if (aktifSüreç >= 0)
			SÜREÇLER[aktifSüreç].başla();
	}
}
