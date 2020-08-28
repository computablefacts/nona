// Generated by Snowball 2.0.0 - https://snowballstem.org/

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;

/**
 * This class implements the stemming algorithm defined by a snowball script.
 * <p>
 * Generated by Snowball 2.0.0 - https://snowballstem.org/
 * </p>
 */
@SuppressWarnings("unused")
public class basqueStemmer extends org.tartarus.snowball.SnowballStemmer {

  private static final long serialVersionUID = 1L;

  private final static Among a_0[] = {new Among("idea", -1, 1), new Among("bidea", 0, 1),
      new Among("kidea", 0, 1), new Among("pidea", 0, 1), new Among("kundea", -1, 1),
      new Among("galea", -1, 1), new Among("tailea", -1, 1), new Among("tzailea", -1, 1),
      new Among("gunea", -1, 1), new Among("kunea", -1, 1), new Among("tzaga", -1, 1),
      new Among("gaia", -1, 1), new Among("aldia", -1, 1), new Among("taldia", 12, 1),
      new Among("karia", -1, 1), new Among("garria", -1, 2), new Among("karria", -1, 1),
      new Among("ka", -1, 1), new Among("tzaka", 17, 1), new Among("la", -1, 1),
      new Among("mena", -1, 1), new Among("pena", -1, 1), new Among("kina", -1, 1),
      new Among("ezina", -1, 1), new Among("tezina", 23, 1), new Among("kuna", -1, 1),
      new Among("tuna", -1, 1), new Among("kizuna", -1, 1), new Among("era", -1, 1),
      new Among("bera", 28, 1), new Among("arabera", 29, 4), new Among("kera", 28, 1),
      new Among("pera", 28, 1), new Among("orra", -1, 1), new Among("korra", 33, 1),
      new Among("dura", -1, 1), new Among("gura", -1, 1), new Among("kura", -1, 1),
      new Among("tura", -1, 1), new Among("eta", -1, 1), new Among("keta", 39, 1),
      new Among("gailua", -1, 1), new Among("eza", -1, 1), new Among("erreza", 42, 1),
      new Among("tza", -1, 2), new Among("gaitza", 44, 1), new Among("kaitza", 44, 1),
      new Among("kuntza", 44, 1), new Among("ide", -1, 1), new Among("bide", 48, 1),
      new Among("kide", 48, 1), new Among("pide", 48, 1), new Among("kunde", -1, 1),
      new Among("tzake", -1, 1), new Among("tzeke", -1, 1), new Among("le", -1, 1),
      new Among("gale", 55, 1), new Among("taile", 55, 1), new Among("tzaile", 55, 1),
      new Among("gune", -1, 1), new Among("kune", -1, 1), new Among("tze", -1, 1),
      new Among("atze", 61, 1), new Among("gai", -1, 1), new Among("aldi", -1, 1),
      new Among("taldi", 64, 1), new Among("ki", -1, 1), new Among("ari", -1, 1),
      new Among("kari", 67, 1), new Among("lari", 67, 1), new Among("tari", 67, 1),
      new Among("etari", 70, 1), new Among("garri", -1, 2), new Among("karri", -1, 1),
      new Among("arazi", -1, 1), new Among("tarazi", 74, 1), new Among("an", -1, 1),
      new Among("ean", 76, 1), new Among("rean", 77, 1), new Among("kan", 76, 1),
      new Among("etan", 76, 1), new Among("atseden", -1, 3), new Among("men", -1, 1),
      new Among("pen", -1, 1), new Among("kin", -1, 1), new Among("rekin", 84, 1),
      new Among("ezin", -1, 1), new Among("tezin", 86, 1), new Among("tun", -1, 1),
      new Among("kizun", -1, 1), new Among("go", -1, 1), new Among("ago", 90, 1),
      new Among("tio", -1, 1), new Among("dako", -1, 1), new Among("or", -1, 1),
      new Among("kor", 94, 1), new Among("tzat", -1, 1), new Among("du", -1, 1),
      new Among("gailu", -1, 1), new Among("tu", -1, 1), new Among("atu", 99, 1),
      new Among("aldatu", 100, 1), new Among("tatu", 100, 1), new Among("baditu", 99, 5),
      new Among("ez", -1, 1), new Among("errez", 104, 1), new Among("tzez", 104, 1),
      new Among("gaitz", -1, 1), new Among("kaitz", -1, 1)};

  private final static Among a_1[] = {new Among("ada", -1, 1), new Among("kada", 0, 1),
      new Among("anda", -1, 1), new Among("denda", -1, 1), new Among("gabea", -1, 1),
      new Among("kabea", -1, 1), new Among("aldea", -1, 1), new Among("kaldea", 6, 1),
      new Among("taldea", 6, 1), new Among("ordea", -1, 1), new Among("zalea", -1, 1),
      new Among("tzalea", 10, 1), new Among("gilea", -1, 1), new Among("emea", -1, 1),
      new Among("kumea", -1, 1), new Among("nea", -1, 1), new Among("enea", 15, 1),
      new Among("zionea", 15, 1), new Among("unea", 15, 1), new Among("gunea", 18, 1),
      new Among("pea", -1, 1), new Among("aurrea", -1, 1), new Among("tea", -1, 1),
      new Among("kotea", 22, 1), new Among("artea", 22, 1), new Among("ostea", 22, 1),
      new Among("etxea", -1, 1), new Among("ga", -1, 1), new Among("anga", 27, 1),
      new Among("gaia", -1, 1), new Among("aldia", -1, 1), new Among("taldia", 30, 1),
      new Among("handia", -1, 1), new Among("mendia", -1, 1), new Among("geia", -1, 1),
      new Among("egia", -1, 1), new Among("degia", 35, 1), new Among("tegia", 35, 1),
      new Among("nahia", -1, 1), new Among("ohia", -1, 1), new Among("kia", -1, 1),
      new Among("tokia", 40, 1), new Among("oia", -1, 1), new Among("koia", 42, 1),
      new Among("aria", -1, 1), new Among("karia", 44, 1), new Among("laria", 44, 1),
      new Among("taria", 44, 1), new Among("eria", -1, 1), new Among("keria", 48, 1),
      new Among("teria", 48, 1), new Among("garria", -1, 2), new Among("larria", -1, 1),
      new Among("kirria", -1, 1), new Among("duria", -1, 1), new Among("asia", -1, 1),
      new Among("tia", -1, 1), new Among("ezia", -1, 1), new Among("bizia", -1, 1),
      new Among("ontzia", -1, 1), new Among("ka", -1, 1), new Among("joka", 60, 3),
      new Among("aurka", 60, 10), new Among("ska", 60, 1), new Among("xka", 60, 1),
      new Among("zka", 60, 1), new Among("gibela", -1, 1), new Among("gela", -1, 1),
      new Among("kaila", -1, 1), new Among("skila", -1, 1), new Among("tila", -1, 1),
      new Among("ola", -1, 1), new Among("na", -1, 1), new Among("kana", 72, 1),
      new Among("ena", 72, 1), new Among("garrena", 74, 1), new Among("gerrena", 74, 1),
      new Among("urrena", 74, 1), new Among("zaina", 72, 1), new Among("tzaina", 78, 1),
      new Among("kina", 72, 1), new Among("mina", 72, 1), new Among("garna", 72, 1),
      new Among("una", 72, 1), new Among("duna", 83, 1), new Among("asuna", 83, 1),
      new Among("tasuna", 85, 1), new Among("ondoa", -1, 1), new Among("kondoa", 87, 1),
      new Among("ngoa", -1, 1), new Among("zioa", -1, 1), new Among("koa", -1, 1),
      new Among("takoa", 91, 1), new Among("zkoa", 91, 1), new Among("noa", -1, 1),
      new Among("zinoa", 94, 1), new Among("aroa", -1, 1), new Among("taroa", 96, 1),
      new Among("zaroa", 96, 1), new Among("eroa", -1, 1), new Among("oroa", -1, 1),
      new Among("osoa", -1, 1), new Among("toa", -1, 1), new Among("ttoa", 102, 1),
      new Among("ztoa", 102, 1), new Among("txoa", -1, 1), new Among("tzoa", -1, 1),
      new Among("\u00F1oa", -1, 1), new Among("ra", -1, 1), new Among("ara", 108, 1),
      new Among("dara", 109, 1), new Among("liara", 109, 1), new Among("tiara", 109, 1),
      new Among("tara", 109, 1), new Among("etara", 113, 1), new Among("tzara", 109, 1),
      new Among("bera", 108, 1), new Among("kera", 108, 1), new Among("pera", 108, 1),
      new Among("ora", 108, 2), new Among("tzarra", 108, 1), new Among("korra", 108, 1),
      new Among("tra", 108, 1), new Among("sa", -1, 1), new Among("osa", 123, 1),
      new Among("ta", -1, 1), new Among("eta", 125, 1), new Among("keta", 126, 1),
      new Among("sta", 125, 1), new Among("dua", -1, 1), new Among("mendua", 129, 1),
      new Among("ordua", 129, 1), new Among("lekua", -1, 1), new Among("burua", -1, 1),
      new Among("durua", -1, 1), new Among("tsua", -1, 1), new Among("tua", -1, 1),
      new Among("mentua", 136, 1), new Among("estua", 136, 1), new Among("txua", -1, 1),
      new Among("zua", -1, 1), new Among("tzua", 140, 1), new Among("za", -1, 1),
      new Among("eza", 142, 1), new Among("eroza", 142, 1), new Among("tza", 142, 2),
      new Among("koitza", 145, 1), new Among("antza", 145, 1), new Among("gintza", 145, 1),
      new Among("kintza", 145, 1), new Among("kuntza", 145, 1), new Among("gabe", -1, 1),
      new Among("kabe", -1, 1), new Among("kide", -1, 1), new Among("alde", -1, 1),
      new Among("kalde", 154, 1), new Among("talde", 154, 1), new Among("orde", -1, 1),
      new Among("ge", -1, 1), new Among("zale", -1, 1), new Among("tzale", 159, 1),
      new Among("gile", -1, 1), new Among("eme", -1, 1), new Among("kume", -1, 1),
      new Among("ne", -1, 1), new Among("zione", 164, 1), new Among("une", 164, 1),
      new Among("gune", 166, 1), new Among("pe", -1, 1), new Among("aurre", -1, 1),
      new Among("te", -1, 1), new Among("kote", 170, 1), new Among("arte", 170, 1),
      new Among("oste", 170, 1), new Among("etxe", -1, 1), new Among("gai", -1, 1),
      new Among("di", -1, 1), new Among("aldi", 176, 1), new Among("taldi", 177, 1),
      new Among("geldi", 176, 8), new Among("handi", 176, 1), new Among("mendi", 176, 1),
      new Among("gei", -1, 1), new Among("egi", -1, 1), new Among("degi", 183, 1),
      new Among("tegi", 183, 1), new Among("nahi", -1, 1), new Among("ohi", -1, 1),
      new Among("ki", -1, 1), new Among("toki", 188, 1), new Among("oi", -1, 1),
      new Among("goi", 190, 1), new Among("koi", 190, 1), new Among("ari", -1, 1),
      new Among("kari", 193, 1), new Among("lari", 193, 1), new Among("tari", 193, 1),
      new Among("garri", -1, 2), new Among("larri", -1, 1), new Among("kirri", -1, 1),
      new Among("duri", -1, 1), new Among("asi", -1, 1), new Among("ti", -1, 1),
      new Among("ontzi", -1, 1), new Among("\u00F1i", -1, 1), new Among("ak", -1, 1),
      new Among("ek", -1, 1), new Among("tarik", -1, 1), new Among("gibel", -1, 1),
      new Among("ail", -1, 1), new Among("kail", 209, 1), new Among("kan", -1, 1),
      new Among("tan", -1, 1), new Among("etan", 212, 1), new Among("en", -1, 4),
      new Among("ren", 214, 2), new Among("garren", 215, 1), new Among("gerren", 215, 1),
      new Among("urren", 215, 1), new Among("ten", 214, 4), new Among("tzen", 214, 4),
      new Among("zain", -1, 1), new Among("tzain", 221, 1), new Among("kin", -1, 1),
      new Among("min", -1, 1), new Among("dun", -1, 1), new Among("asun", -1, 1),
      new Among("tasun", 226, 1), new Among("aizun", -1, 1), new Among("ondo", -1, 1),
      new Among("kondo", 229, 1), new Among("go", -1, 1), new Among("ngo", 231, 1),
      new Among("zio", -1, 1), new Among("ko", -1, 1), new Among("trako", 234, 5),
      new Among("tako", 234, 1), new Among("etako", 236, 1), new Among("eko", 234, 1),
      new Among("tariko", 234, 1), new Among("sko", 234, 1), new Among("tuko", 234, 1),
      new Among("minutuko", 241, 6), new Among("zko", 234, 1), new Among("no", -1, 1),
      new Among("zino", 244, 1), new Among("ro", -1, 1), new Among("aro", 246, 1),
      new Among("igaro", 247, 9), new Among("taro", 247, 1), new Among("zaro", 247, 1),
      new Among("ero", 246, 1), new Among("giro", 246, 1), new Among("oro", 246, 1),
      new Among("oso", -1, 1), new Among("to", -1, 1), new Among("tto", 255, 1),
      new Among("zto", 255, 1), new Among("txo", -1, 1), new Among("tzo", -1, 1),
      new Among("gintzo", 259, 1), new Among("\u00F1o", -1, 1), new Among("zp", -1, 1),
      new Among("ar", -1, 1), new Among("dar", 263, 1), new Among("behar", 263, 1),
      new Among("zehar", 263, 7), new Among("liar", 263, 1), new Among("tiar", 263, 1),
      new Among("tar", 263, 1), new Among("tzar", 263, 1), new Among("or", -1, 2),
      new Among("kor", 271, 1), new Among("os", -1, 1), new Among("ket", -1, 1),
      new Among("du", -1, 1), new Among("mendu", 275, 1), new Among("ordu", 275, 1),
      new Among("leku", -1, 1), new Among("buru", -1, 2), new Among("duru", -1, 1),
      new Among("tsu", -1, 1), new Among("tu", -1, 1), new Among("tatu", 282, 4),
      new Among("mentu", 282, 1), new Among("estu", 282, 1), new Among("txu", -1, 1),
      new Among("zu", -1, 1), new Among("tzu", 287, 1), new Among("gintzu", 288, 1),
      new Among("z", -1, 1), new Among("ez", 290, 1), new Among("eroz", 290, 1),
      new Among("tz", 290, 1), new Among("koitz", 293, 1)};

  private final static Among a_2[] = {new Among("zlea", -1, 2), new Among("keria", -1, 1),
      new Among("la", -1, 1), new Among("era", -1, 1), new Among("dade", -1, 1),
      new Among("tade", -1, 1), new Among("date", -1, 1), new Among("tate", -1, 1),
      new Among("gi", -1, 1), new Among("ki", -1, 1), new Among("ik", -1, 1),
      new Among("lanik", 10, 1), new Among("rik", 10, 1), new Among("larik", 12, 1),
      new Among("ztik", 10, 1), new Among("go", -1, 1), new Among("ro", -1, 1),
      new Among("ero", 16, 1), new Among("to", -1, 1)};

  private static final char g_v[] = {17, 65, 16};

  private int I_p2;
  private int I_p1;
  private int I_pV;


  private boolean r_mark_regions() {
    // (, line 23
    I_pV = limit;
    I_p1 = limit;
    I_p2 = limit;
    // do, line 29
    int v_1 = cursor;
    lab0: {
      // (, line 29
      // or, line 31
      lab1: {
        int v_2 = cursor;
        lab2: {
          // (, line 30
          if (!(in_grouping(g_v, 97, 117))) {
            break lab2;
          }
          // or, line 30
          lab3: {
            int v_3 = cursor;
            lab4: {
              // (, line 30
              if (!(out_grouping(g_v, 97, 117))) {
                break lab4;
              }
              // gopast, line 30
              golab5: while (true) {
                lab6: {
                  if (!(in_grouping(g_v, 97, 117))) {
                    break lab6;
                  }
                  break golab5;
                }
                if (cursor >= limit) {
                  break lab4;
                }
                cursor++;
              }
              break lab3;
            }
            cursor = v_3;
            // (, line 30
            if (!(in_grouping(g_v, 97, 117))) {
              break lab2;
            }
            // gopast, line 30
            golab7: while (true) {
              lab8: {
                if (!(out_grouping(g_v, 97, 117))) {
                  break lab8;
                }
                break golab7;
              }
              if (cursor >= limit) {
                break lab2;
              }
              cursor++;
            }
          }
          break lab1;
        }
        cursor = v_2;
        // (, line 32
        if (!(out_grouping(g_v, 97, 117))) {
          break lab0;
        }
        // or, line 32
        lab9: {
          int v_6 = cursor;
          lab10: {
            // (, line 32
            if (!(out_grouping(g_v, 97, 117))) {
              break lab10;
            }
            // gopast, line 32
            golab11: while (true) {
              lab12: {
                if (!(in_grouping(g_v, 97, 117))) {
                  break lab12;
                }
                break golab11;
              }
              if (cursor >= limit) {
                break lab10;
              }
              cursor++;
            }
            break lab9;
          }
          cursor = v_6;
          // (, line 32
          if (!(in_grouping(g_v, 97, 117))) {
            break lab0;
          }
          // next, line 32
          if (cursor >= limit) {
            break lab0;
          }
          cursor++;
        }
      }
      // setmark pV, line 33
      I_pV = cursor;
    }
    cursor = v_1;
    // do, line 35
    int v_8 = cursor;
    lab13: {
      // (, line 35
      // gopast, line 36
      golab14: while (true) {
        lab15: {
          if (!(in_grouping(g_v, 97, 117))) {
            break lab15;
          }
          break golab14;
        }
        if (cursor >= limit) {
          break lab13;
        }
        cursor++;
      }
      // gopast, line 36
      golab16: while (true) {
        lab17: {
          if (!(out_grouping(g_v, 97, 117))) {
            break lab17;
          }
          break golab16;
        }
        if (cursor >= limit) {
          break lab13;
        }
        cursor++;
      }
      // setmark p1, line 36
      I_p1 = cursor;
      // gopast, line 37
      golab18: while (true) {
        lab19: {
          if (!(in_grouping(g_v, 97, 117))) {
            break lab19;
          }
          break golab18;
        }
        if (cursor >= limit) {
          break lab13;
        }
        cursor++;
      }
      // gopast, line 37
      golab20: while (true) {
        lab21: {
          if (!(out_grouping(g_v, 97, 117))) {
            break lab21;
          }
          break golab20;
        }
        if (cursor >= limit) {
          break lab13;
        }
        cursor++;
      }
      // setmark p2, line 37
      I_p2 = cursor;
    }
    cursor = v_8;
    return true;
  }

  private boolean r_RV() {
    if (!(I_pV <= cursor)) {
      return false;
    }
    return true;
  }

  private boolean r_R2() {
    if (!(I_p2 <= cursor)) {
      return false;
    }
    return true;
  }

  private boolean r_R1() {
    if (!(I_p1 <= cursor)) {
      return false;
    }
    return true;
  }

  private boolean r_aditzak() {
    int among_var;
    // (, line 47
    // [, line 48
    ket = cursor;
    // substring, line 48
    among_var = find_among_b(a_0);
    if (among_var == 0) {
      return false;
    }
    // ], line 48
    bra = cursor;
    switch (among_var) {
      case 1:
        // (, line 59
        // call RV, line 59
        if (!r_RV()) {
          return false;
        }
        // delete, line 59
        slice_del();
        break;
      case 2:
        // (, line 61
        // call R2, line 61
        if (!r_R2()) {
          return false;
        }
        // delete, line 61
        slice_del();
        break;
      case 3:
        // (, line 63
        // <-, line 63
        slice_from("atseden");
        break;
      case 4:
        // (, line 65
        // <-, line 65
        slice_from("arabera");
        break;
      case 5:
        // (, line 67
        // <-, line 67
        slice_from("baditu");
        break;
    }
    return true;
  }

  private boolean r_izenak() {
    int among_var;
    // (, line 72
    // [, line 73
    ket = cursor;
    // substring, line 73
    among_var = find_among_b(a_1);
    if (among_var == 0) {
      return false;
    }
    // ], line 73
    bra = cursor;
    switch (among_var) {
      case 1:
        // (, line 103
        // call RV, line 103
        if (!r_RV()) {
          return false;
        }
        // delete, line 103
        slice_del();
        break;
      case 2:
        // (, line 105
        // call R2, line 105
        if (!r_R2()) {
          return false;
        }
        // delete, line 105
        slice_del();
        break;
      case 3:
        // (, line 107
        // <-, line 107
        slice_from("jok");
        break;
      case 4:
        // (, line 109
        // call R1, line 109
        if (!r_R1()) {
          return false;
        }
        // delete, line 109
        slice_del();
        break;
      case 5:
        // (, line 111
        // <-, line 111
        slice_from("tra");
        break;
      case 6:
        // (, line 113
        // <-, line 113
        slice_from("minutu");
        break;
      case 7:
        // (, line 115
        // <-, line 115
        slice_from("zehar");
        break;
      case 8:
        // (, line 117
        // <-, line 117
        slice_from("geldi");
        break;
      case 9:
        // (, line 119
        // <-, line 119
        slice_from("igaro");
        break;
      case 10:
        // (, line 121
        // <-, line 121
        slice_from("aurka");
        break;
    }
    return true;
  }

  private boolean r_adjetiboak() {
    int among_var;
    // (, line 125
    // [, line 126
    ket = cursor;
    // substring, line 126
    among_var = find_among_b(a_2);
    if (among_var == 0) {
      return false;
    }
    // ], line 126
    bra = cursor;
    switch (among_var) {
      case 1:
        // (, line 129
        // call RV, line 129
        if (!r_RV()) {
          return false;
        }
        // delete, line 129
        slice_del();
        break;
      case 2:
        // (, line 131
        // <-, line 131
        slice_from("z");
        break;
    }
    return true;
  }

  public boolean stem() {
    // (, line 137
    // do, line 138
    // call mark_regions, line 138
    r_mark_regions();
    // backwards, line 139
    limit_backward = cursor;
    cursor = limit;
    // (, line 139
    // repeat, line 140
    while (true) {
      int v_2 = limit - cursor;
      lab0: {
        // call aditzak, line 140
        if (!r_aditzak()) {
          break lab0;
        }
        continue;
      }
      cursor = limit - v_2;
      break;
    }
    // repeat, line 141
    while (true) {
      int v_3 = limit - cursor;
      lab1: {
        // call izenak, line 141
        if (!r_izenak()) {
          break lab1;
        }
        continue;
      }
      cursor = limit - v_3;
      break;
    }
    // do, line 142
    int v_4 = limit - cursor;
    // call adjetiboak, line 142
    r_adjetiboak();
    cursor = limit - v_4;
    cursor = limit_backward;
    return true;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof basqueStemmer;
  }

  @Override
  public int hashCode() {
    return basqueStemmer.class.getName().hashCode();
  }



}

