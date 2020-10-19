/**
 * başaşağıderebeyi.soyutkuruluş.dünya.DünyaArayüzü.java
 * 0.3 / 19 Eki 2020 / 10:24:20
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.dünya;

import başaşağıderebeyi.matematik.*;
import başaşağıderebeyi.motor.*;
import başaşağıderebeyi.soyutkuruluş.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

public class DünyaArayüzü {
	public static final float KÖŞE_YARIÇAPI = 0.05F;
	public static final Vektör2 KÖŞE_ÇEYREK_DAİRE_VEKTÖRÜ = new Vektör2(KÖŞE_YARIÇAPI, KÖŞE_YARIÇAPI);
	public static final Vektör2 KÖŞE_DAİRE_VEKTÖRÜ = new Vektör2(KÖŞE_ÇEYREK_DAİRE_VEKTÖRÜ).çarp(2.0F);
	public static final float ŞEHİR_YARIÇAPI = 0.15F;
	public static final Vektör2 ŞEHİR_ÇEYREK_DAİRE_VEKTÖRÜ = new Vektör2(ŞEHİR_YARIÇAPI, ŞEHİR_YARIÇAPI);
	public static final Vektör2 ŞEHİR_DAİRE_VEKTÖRÜ = new Vektör2(ŞEHİR_ÇEYREK_DAİRE_VEKTÖRÜ).çarp(2.0F);
	public static final float ÖLÇEK_TABANI = 1.5F;
	public static final int YAKINLAŞTIRMA_BAŞLANGIÇ = 10;
	public static final int YAKINLAŞTIRMA_TAVAN = 12;
	public static final int YAKINLAŞTIRMA_TABAN = 8;
	public static final DecimalFormat ÜRETİM_SAYI_ŞABLONU = new DecimalFormat("0.00");
	public static final DateFormat TAKVİM_TARİHİ_ŞABLONU = DateFormat.getDateInstance(DateFormat.LONG);
	public static final float[] GÜN_SÜRELERİ = { 3.0F, 1.0F, 0.3F, 0.1F, 0.03F, 0.01F, 0.003F, 0.0F };
	
	static {
		for (int i = 0; i < GÜN_SÜRELERİ.length; i++)
			GÜN_SÜRELERİ[i] *= 1000000000.0F;
	}
	
	public final Vektör2 kamera;
	
	public int yakınlaştırma;
	public float ölçek;
	public float sonZaman;
	public int hız;
	public float bekleme;
	public boolean duraklatıldı;
	
	public DünyaArayüzü() {
		kamera = new Vektör2();
		başlangıçKamerası();
		sonZaman = 0.0F;
		hız = 3;
		bekleme = 0.0F;
	}
	
	public void kare(final Girdi girdi, final Graphics2D çizer, final Dünya dünya) {
		final float şimdikiZaman = Motor.zaman();
		if (!duraklatıldı) {
			if (sonZaman != 0.0F)
				bekleme += şimdikiZaman - sonZaman;
			sonZaman = şimdikiZaman;
			if (bekleme >= GÜN_SÜRELERİ[hız]) {
				bekleme -= GÜN_SÜRELERİ[hız];
				final int eskiAy = dünya.takvim.get(Calendar.MONTH);
				dünya.takvim.add(Calendar.DAY_OF_MONTH, 1);
				dünya.günlük();
				if (eskiAy != dünya.takvim.get(Calendar.MONTH)) {
					dünya.aylık();
					if (eskiAy == 11)
						dünya.yıllık();
				}
			}
		}
		if (girdi.tuşBasıldı[KeyEvent.VK_SPACE]) {
			duraklatıldı = !duraklatıldı;
			bekleme = 0.0F;
			sonZaman = 0.0F;
		}
		if (girdi.tuşBasıldı[KeyEvent.VK_UP] && hız < GÜN_SÜRELERİ.length - 1) {
			hız++;
			bekleme = 0.0F;
			sonZaman = 0.0F;
		}
		if (girdi.tuşBasıldı[KeyEvent.VK_DOWN] && hız > 0) {
			hız--;
			bekleme = 0.0F;
			sonZaman = 0.0F;
		}
		if (girdi.tekerlek != 0) {
			final Vektör2 imleçDünyaEski = dünyaKoordinatına(girdi.imleç, new Vektör2());
			yakınlaştırma += girdi.tekerlek;
			ölçeğiHesapla();
			final Vektör2 imleçDünyaYeni = dünyaKoordinatına(girdi.imleç, new Vektör2());
			kamera.çıkar(imleçDünyaYeni.çıkar(imleçDünyaEski).çarp(ölçek));
		}
		if (girdi.düğmeAşağı[MouseEvent.BUTTON2])
			kamera.çıkar(girdi.imleçDeğişimi);
		if (girdi.tuşBasıldı[KeyEvent.VK_K])
			başlangıçKamerası();
		final Vektör2 konum = new Vektör2();
		final Vektör2 başlangıç = new Vektör2();
		final Vektör2 bitiş = new Vektör2();
		final Stroke kalınlık = çizer.getStroke();
		final Stroke yolKalınlığı = new BasicStroke(ölçek / 10.0F);
		for (final Kenar kenar : dünya.kenarlar) {
			final Yol yol = dünya.yollar.get(kenar);
			çizer.setColor(yol != null ? yol.ulus.renk : Color.WHITE);
			çizer.setStroke(yol != null ? yolKalınlığı : kalınlık);
			ekranKoordinatına(kenar.başlangıç.konum, başlangıç).yuvarla();
			ekranKoordinatına(kenar.bitiş.konum, bitiş).yuvarla();
			çizer.drawLine((int)başlangıç.x, (int)başlangıç.y, (int)bitiş.x, (int)bitiş.y);
		}
		çizer.setStroke(kalınlık);
		final Vektör2 köşeDaireÖlçekli = new Vektör2(KÖŞE_DAİRE_VEKTÖRÜ).çarp(ölçek).yuvarla();
		final Vektör2 şehirDaireÖlçekli = new Vektör2(ŞEHİR_DAİRE_VEKTÖRÜ).çarp(ölçek).yuvarla();
		çizer.setFont(new Font("Verdana", Font.ITALIC, (int)Math.round(ölçek / 8.0F)));
		for (final Köşe köşe : dünya.köşeler) {
			final Şehir şehir = dünya.şehirler.get(köşe);
			if (şehir != null) {
				ekranKoordinatına(konum.çıkar(köşe.konum, ŞEHİR_ÇEYREK_DAİRE_VEKTÖRÜ), konum).yuvarla();
				çizer.setColor(şehir.ulus.renk);
				çizer.fillOval((int)konum.x, (int)konum.y, (int)şehirDaireÖlçekli.x, (int)şehirDaireÖlçekli.y);
				ekranKoordinatına(köşe.konum, konum);
				çizer.setColor(Color.BLACK);
				yazıYaz(çizer, (int)konum.x, (int)konum.y, null, ÜRETİM_SAYI_ŞABLONU.format(şehir.seviye));
			} else {
				ekranKoordinatına(konum.çıkar(köşe.konum, KÖŞE_ÇEYREK_DAİRE_VEKTÖRÜ), konum).yuvarla();
				çizer.setColor(Color.WHITE);
				çizer.fillOval((int)konum.x, (int)konum.y, (int)köşeDaireÖlçekli.x, (int)köşeDaireÖlçekli.y);
			}
		}
		çizer.setFont(new Font("Verdana", Font.ITALIC, (int)Math.round(ölçek / 3.0F)));
		for (final Bölge bölge : dünya.bölgeler) {
			ekranKoordinatına(bölge.merkez, konum).yuvarla();
			çizer.setColor(bölge.kaynak.renk);
			yazıYaz(çizer, (int)konum.x, (int)konum.y, null, bölge.kaynak.toString(), ÜRETİM_SAYI_ŞABLONU.format(bölge.üretim));
		}
		çizer.setFont(new Font("Verdana", Font.ITALIC, 16));
		çizer.setColor(Color.WHITE);
		yazıYaz(çizer, (int)Math.round(SoyutKuruluş.GÖRSELLEŞTİRİCİ.boyut.x / 2.0F), 30, Color.BLACK,
				TAKVİM_TARİHİ_ŞABLONU.format(dünya.takvim.getTime()),
				"Hız: " + hız);
		if (duraklatıldı)
			yazıYaz(çizer, (int)Math.round(SoyutKuruluş.GÖRSELLEŞTİRİCİ.boyut.x / 2.0F), 100, Color.BLACK,
				"D U R A K L A T I L D I !");
	}
	
	public Vektör2 ekranKoordinatına(final Vektör2 dünyaKoordinatı, final Vektör2 hedef) {
		return hedef.çarp(dünyaKoordinatı, ölçek).çıkar(kamera);
	}
	
	public Vektör2 dünyaKoordinatına(final Vektör2 ekranKoordinatı, final Vektör2 hedef) {
		return hedef.topla(ekranKoordinatı, kamera).böl(ölçek);
	}
	
	public void ölçeğiHesapla() {
		if (yakınlaştırma > YAKINLAŞTIRMA_TAVAN)
			yakınlaştırma = YAKINLAŞTIRMA_TAVAN;
		else if (yakınlaştırma < YAKINLAŞTIRMA_TABAN)
			yakınlaştırma = YAKINLAŞTIRMA_TABAN;
		ölçek = (float)Math.pow(ÖLÇEK_TABANI, yakınlaştırma);
	}
	
	public void başlangıçKamerası() {
		yakınlaştırma = YAKINLAŞTIRMA_BAŞLANGIÇ;
		ölçeğiHesapla();
		kamera.yaz(SoyutKuruluş.GÖRSELLEŞTİRİCİ.boyut).böl(-2.0F);
	}
	
	public void yazıYaz(final Graphics2D çizer, final int x, int y, final Color arkaplan, final String... yazılar) {
		final FontMetrics yazıTipi = çizer.getFontMetrics();
		final int yükseklik = yazıTipi.getHeight();
		final int arkaplanFazlası = Math.round(yükseklik * 0.1F);
		final Color renk = çizer.getColor();
		y -= yükseklik * yazılar.length / 2;
		for (final String yazı : yazılar) {
			final int kaydırılmışX = x - yazıTipi.stringWidth(yazı) / 2;
			if (arkaplan != null) {
				final int yazıGenişliği = yazıTipi.stringWidth(yazı);
				çizer.setColor(arkaplan);
				çizer.fillRect(kaydırılmışX - arkaplanFazlası, y, yazıGenişliği + arkaplanFazlası * 2, yükseklik);
				çizer.setColor(renk);
			}
			y += yükseklik;
			çizer.drawString(yazı, kaydırılmışX, y - yazıTipi.getDescent());
		}
	}
}
