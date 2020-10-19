/**
 * başaşağıderebeyi.soyutkuruluş.yaratıcı.RastgeleYaratıcı.java
 * 0.3 / 19 Eki 2020 / 10:43:19
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.yaratıcı;

import başaşağıderebeyi.matematik.*;
import başaşağıderebeyi.soyutkuruluş.dünya.*;

import java.util.*;

public class RastgeleYaratıcı implements Yaratıcı {
	public static final int RASTGELE_SATIR_TAVAN = 5;
	public static final int RASTGELE_SATIR_TABAN = 3;
	public static final float ÜRETİM_TAVAN = 5.0F;
	public static final float ÜRETİM_TABAN = 1.0F;
	
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
		return dünya;
	}
}
