/**
 * başaşağıderebeyi.soyutkuruluş.Kaynak.java
 * 0.3 / 19 Eki 2020 / 10:30:08
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.dünya;

import java.awt.*;
import java.util.*;

public enum Kaynak {
	BUĞDAY(new Color(1.0F, 1.0F, 0.0F, 1.0F)),
	KOYUN(new Color(0.8F, 1.0F, 0.8F, 1.0F)),
	CEVHER(new Color(0.7F, 0.7F, 0.7F, 1.0F)),
	ODUN(new Color(0.2F, 0.6F, 0.2F, 1.0F)),
	TUĞLA(new Color(0.8F, 0.5F, 0.2F, 1.0F));
	
	public static final Kaynak[] DEĞERLER = values();
	
	public static Kaynak rastgele(final Random rastgele) {
		return DEĞERLER[rastgele.nextInt(DEĞERLER.length)];
	}
	
	public final int sıra;
	public final Color renk;

	private Kaynak(Color renk) {
		this.sıra = ordinal();
		this.renk = renk;
	}
}
