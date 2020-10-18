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

import java.awt.*;
import java.awt.event.*;

public class SoyutKuruluş implements Uygulama {
	public static final String SÜRÜM = "0.2";
	public static final SoyutKuruluş UYGULAMA = new SoyutKuruluş();
	public static final AWTGörselleştirici GÖRSELLEŞTİRİCİ = new AWTGörselleştirici();
	public static final float KÖŞE_YARIÇAPI = 5.0F;
	public static final Vektör2 KÖŞE_ÇEYREK_DAİRE_VEKTÖRÜ = new Vektör2(KÖŞE_YARIÇAPI, KÖŞE_YARIÇAPI);
	public static final Vektör2 KÖŞE_DAİRE_VEKTÖRÜ = new Vektör2(KÖŞE_ÇEYREK_DAİRE_VEKTÖRÜ).çarp(2.0F);
	public static final Vektör2 KÖŞE_DAİRE_VEKTÖRÜ_YUVARLANMIŞ = new Vektör2(KÖŞE_DAİRE_VEKTÖRÜ).yuvarla();
	
	public static void main(final String[] args) {
		Motor.uygulama = UYGULAMA;
		Motor.görselleştirici = GÖRSELLEŞTİRİCİ;
		GÖRSELLEŞTİRİCİ.başlık = "Soyut Kuruluş srm. " + SÜRÜM;
		GÖRSELLEŞTİRİCİ.boyut.yaz(16.0F * 80.0F, 9.0F * 80.0F);
		GÖRSELLEŞTİRİCİ.arkaplanRengi = new Color(0.1F, 0.1F, 0.2F, 1.0F);
		Motor.başla();
	}
	
	public Dünya dünya;
	public Vektör2 kamera;
	
	@Override
	public void yükle() {
		dünya = new Dünya(0);
		kamera = new Vektör2(GÖRSELLEŞTİRİCİ.boyut).böl(-2.0F);
	}

	@Override
	public void kaydet() {
	}

	@Override
	public void kare() {
		if (GÖRSELLEŞTİRİCİ.girdi.tuşBasıldı[KeyEvent.VK_Y]) {
			dünya = new Dünya(2);
		}
		GÖRSELLEŞTİRİCİ.çizer.setColor(Color.WHITE);
		final Vektör2 konum = new Vektör2();
		final Vektör2 başlangıç = new Vektör2();
		final Vektör2 bitiş = new Vektör2();
		for (final Kenar kenar : dünya.kenarlar) {
			başlangıç.çıkar(kenar.başlangıç, kamera).yuvarla();
			bitiş.çıkar(kenar.bitiş, kamera).yuvarla();
			GÖRSELLEŞTİRİCİ.çizer.drawLine((int)başlangıç.x, (int)başlangıç.y, (int)bitiş.x, (int)bitiş.y);
		}
		for (final Vektör2 köşe : dünya.köşeler) {
			konum.çıkar(köşe, kamera).çıkar(KÖŞE_ÇEYREK_DAİRE_VEKTÖRÜ).yuvarla();
			GÖRSELLEŞTİRİCİ.çizer.fillOval((int)konum.x, (int)konum.y, (int)KÖŞE_DAİRE_VEKTÖRÜ_YUVARLANMIŞ.x, (int)KÖŞE_DAİRE_VEKTÖRÜ_YUVARLANMIŞ.y);
		}
	}

	@Override
	public void saniye() {
	}
}
