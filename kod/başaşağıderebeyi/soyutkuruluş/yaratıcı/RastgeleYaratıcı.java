/**
 * başaşağıderebeyi.soyutkuruluş.yaratıcı.RastgeleYaratıcı.java
 * 0.3 / 19 Eki 2020 / 10:43:19
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.yaratıcı;

import başaşağıderebeyi.matematik.*;
import başaşağıderebeyi.soyutkuruluş.dünya.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;
import başaşağıderebeyi.soyutkuruluş.zeka.*;

import java.awt.*;
import java.util.*;

public class RastgeleYaratıcı implements Yaratıcı {
	public static final int RASTGELE_SATIR_TAVAN = 3;
	public static final int RASTGELE_SATIR_TABAN = 3;
	public static final float ÜRETİM_TAVAN = 5.0F;
	public static final float ÜRETİM_TABAN = 1.0F;
	public static final Color[] ULUS_RENKLERİ = {
			new Color(1.0F, 0.4F, 0.4F, 1.0F),
			new Color(0.4F, 1.0F, 0.4F, 1.0F),
			new Color(0.4F, 0.4F, 1.0F, 1.0F),
			new Color(0.6F, 0.6F, 0.6F, 1.0F)
	};
	
	private final Random rastgele;
	
	public RastgeleYaratıcı(final Random rastgele) {
		this.rastgele = rastgele;
	}
	
	public RastgeleYaratıcı() {
		this(new Random());
	}

	@Override
	public Dünya yarat() {
		final Dünya dünya = new Dünya();
		final int satırYüksekliği = rastgele.nextInt(RASTGELE_SATIR_TAVAN - RASTGELE_SATIR_TABAN + 1) + RASTGELE_SATIR_TABAN;
		int satırSaçılması = satırYüksekliği + 1;
		for (int i = -satırYüksekliği; i <= satırYüksekliği; i++) {
			final int satırGenişliği = satırSaçılması / 2;
			yatay:for (int j = -satırGenişliği; j <= satırGenişliği; j++) {
				final Vektör2 merkez = new Vektör2(j * Bölge.KENAR_SİNÜS * 2.0F, i * Bölge.KENAR_KOSİNÜS * 3.0F);
				if (satırSaçılması % 2 == 0) {
					if (j == 0)
						continue yatay;
					merkez.x += Bölge.KENAR_SİNÜS * (j < 0 ? 1.0F : -1.0F);
				}
				final Bölge bölge = new Bölge(dünya, merkez, Kaynak.rastgele(rastgele), rastgele.nextFloat() * (ÜRETİM_TAVAN - ÜRETİM_TABAN) + ÜRETİM_TABAN);
				dünya.bölgeler.add(bölge);
			}
			if (i < 0)
				satırSaçılması++;
			else
				satırSaçılması--;
		}
		dünya.şekliGüncelle();
		for (int i = 0; i < ULUS_RENKLERİ.length; i++) {
			dünya.ulusOluştur(ULUS_RENKLERİ[i]);
			final Ulus ulus = dünya.uluslar.get(i);
			ulus.zeka = rastgele.nextInt(2) == 0 ? new DurgunZeka(ulus) : new BasitZeka(ulus);
			Köşe köşe = null;
			do {
				köşe = dünya.köşeler.get(rastgele.nextInt(dünya.köşeler.size()));
			} while (!dünya.şehirOluşturabilirMi(ulus, köşe));
			dünya.başlangıçŞehriOluştur(ulus, köşe);
		}
		return dünya;
	}
}
