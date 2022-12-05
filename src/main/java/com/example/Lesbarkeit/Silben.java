package com.example.Lesbarkeit;

import java.util.*;

/**
 * Silbentrennung in Java
 */
public class Silben {
  public static void test(String str, String strExpected) {
    System.out.println("=====================================================================");

    System.out.println(str);
    String strResult = arr2Str(determineWord(str) , "-") ;

    System.out.println(" -> " + strResult) ;
    if (!strResult.equals(strExpected)) {
      throw new RuntimeException("expected was " + strExpected) ;
    }
  }


  private static HashMap<String, String[]> wordHashMap = new HashMap<>();

  private static HashSet<String> syllHashSet = newSyllHashSet();

  private static HashSet<String> newSyllHashSet() {
    HashSet<String> newHashSet = new HashSet<String>();

    newHashSet.add("an") ;
    newHashSet.add("trag") ;
    newHashSet.add("schäfts") ;
    newHashSet.add("chen") ;
    newHashSet.add("gen") ;

    newHashSet.add("dung") ;
    newHashSet.add("nung") ;
    newHashSet.add("rung") ;
    newHashSet.add("tung") ;

    newHashSet.add("dungs") ;
    newHashSet.add("nungs") ;
    newHashSet.add("rungs") ;
    newHashSet.add("tungs") ;

    newHashSet.add("ent") ;
    newHashSet.add("auf") ;
    newHashSet.add("trag") ;

    newHashSet.add("lis") ;
    newHashSet.add("in") ;
    newHashSet.add("tion") ;
    newHashSet.add("grund") ;
    newHashSet.add("gründ") ;
    newHashSet.add("änderung") ;
    newHashSet.add("be") ;
    newHashSet.add("ver") ;
    newHashSet.add("lauf") ;
    newHashSet.add("schreib") ;
    newHashSet.add("ngen") ;
    newHashSet.add("ten") ;
    newHashSet.add("treten") ;
    newHashSet.add("mit") ;
    newHashSet.add("pro") ;
    newHashSet.add("jekt") ;
    newHashSet.add("re") ;
    newHashSet.add("agieren") ;
    newHashSet.add("arbeit") ;
    newHashSet.add("schaft") ;
    newHashSet.add("schaf") ;
    newHashSet.add("tränke") ;
    newHashSet.add("gesamt") ;
    newHashSet.add("samt") ;
    newHashSet.add("autor") ;
    newHashSet.add("rungs") ;
    newHashSet.add("text") ;
    return   newHashSet;

  }


  public static String[] determineWord(String str) {
    String[] retStrArr = null ;

    retStrArr = wordHashMap.get(str);
    if (retStrArr != null) {
      return retStrArr;
    }
    ArrayList<String> arrList = new ArrayList<>();

    str = str.trim();

    while (str.length() > 0) {
      System.out.println("-----------------------------------------------------");
      String syllable = syllableAnalyse(str) ;


      str = str.substring(syllable.length()) ;
      arrList.add(syllable) ;
    }

    retStrArr = (String[]) arrList.toArray(new String[0]) ;
    int countSyllables=0;
    for(String n : retStrArr){
      countSyllables ++;
    }
    System.out.println("Silben: " + countSyllables);

    wordHashMap.put(str , retStrArr) ;

    return retStrArr;
  }


  private static String syllableAnalyse(String str) {
    int i = 0 ;
    boolean token = false ;
    boolean cont = true ;

    while (cont) {
      cont = false;

      boolean match = false;

      for (;!match && i < str.length() && isConsonant(str.charAt(i)) ; i++) {
        match = checkKnownSyllable(str.substring(0 , Math.min(i , str.length())) ,
            str.substring(Math.min(i , str.length()))) ;
      }

      for (;!match && i < str.length() && isVocal(str.charAt(i)) ; i++) {
        match = checkKnownSyllable(str.substring(0 , Math.min(i , str.length())) ,
            str.substring(Math.min(i , str.length())));
      }

      for (;!match && (i < (str.length() - 1)) && isConsonant(str.charAt(i + 1)) ; i++) {
        match = checkKnownSyllable(str.substring(0 , Math.min(i , str.length())) ,
            str.substring(Math.min(i , str.length())));
      }

      if (match) {
        i--;
      }

      else if (checkKnownSyllable(str.substring(0 , Math.min(i , str.length())) ,
          str.substring(Math.min(i , str.length())))) {
        System.out.println("Trennung zweier bekannter Silben aufgetreten " + str.substring(i)) ;
      }

      else if (checkKnownSyllable(str.substring(0 , Math.min(i + 1 , str.length())) ,
          str.substring(Math.min(i + 1, str.length())))) {
        System.out.println("Trennung zweier bekannter Silben an Folgeposition 1 aufgetreten " + str.substring(i)) ;
        i += 1 ;
      }

      else if (checkKnownSyllable(str.substring(0 , Math.min(i + 2 , str.length())) ,
          str.substring(Math.min(i + 2, str.length())))) {
        System.out.println("Trennung zweier bekannter Silben an Folgeposition 2 aufgetreten " + str.substring(i)) ;
        i += 2 ;
      }

       else if (checkKnownSyllable(str.substring(0 , Math.min(i + 3 , str.length())) ,
       str.substring(Math.min(i + 3, str.length())))) {
       System.out.println("Trennung zweier bekannter Silben an Folgeposition 3 aufgetreten " + str.substring(i)) ;
       i += 3 ;
       }

      else if (i > 1 && (i < str.length() - 1) && str.substring(i - 1).startsWith("st")) {
        System.out.println("st wurde getrennt " + str.substring(i)) ;
        i--;
      }

      else if ((i < str.length() - 1) && str.substring(i).startsWith("st")) {
        System.out.println("st am Ende der Silbe " + str.substring(i)) ;
        i += 2;
      }

      else if ((i < str.length() - 1) && str.substring(i).startsWith("ft")) {
        System.out.println("ft am Ende der Silbe " + str.substring(i)) ;
        i += 2;
      }

      else if (i > 2 && (i < str.length() - 1) && str.substring(i - 2).startsWith("sch")) {
        System.out.println("sch wurde getrennt " + str.substring(i)) ;
        i -= 2;
      }

      else if (i > 1 && (i < str.length() - 1) && str.substring(i - 1).startsWith("ch")) {
        System.out.println("ch wurde getrennt " + str.substring(i)) ;
        i--;
      }

      else if (i > 2 && (i < str.length() - 1) && str.substring(i - 2).startsWith("str")) {
        System.out.println("str am Anfang der nächsten Silbe " + str.substring(i)) ;
        i -= 2;
      }


      else if ((i > 1 && (i < str.length() - 1) && str.substring(i - 1).startsWith("c"))
          && (i < str.length() - 1) && str.substring(i).startsWith("k")) {
        System.out.println("ck wird in kk umgewandelt " + str.substring(i)) ;
        token = true ;
      }

      else if ((i > 1 && (i < str.length() - 1) && str.substring(i - 1).toLowerCase().startsWith("n"))
          && (i < str.length() - 1) && str.substring(i).startsWith("g")
          ) {
        System.out.println("ng wird nicht getrennt " + str.substring(i)) ;
        i++;
        cont = true;
      }


      else if (i > 0 && (i < str.length() - 1) && isVocal(str.charAt(i + 1))) {
        System.out.println("ein Vocal folgt sofort nach dem endenden Konsonant " + str.substring(i)) ;
        ;
      }

      else if (i < str.length() && isConsonant(str.charAt(i))) {
        System.out.println("Ende der Silbe mit Konsonant " + str.substring(i)) ;
        i++;
      }

      else if (isAllConsonantes(str.substring(i))) {
        System.out.println("es folgen nur noch Konsonanten bis zum Ende des Wortes " + str.substring(i)) ;
        i = str.length();
      }

      else {
        System.out.println("keine Regel hat gezogen") ;
      }

    }

    String retStr = str.substring(0 , i) ;

    if (token) {
      retStr = retStr.substring(0 , retStr.length() - 1) + "k" ;
    }

    return retStr ;
  }

  public static boolean isConsonant(char ch) {
    return !isVocal(ch) ;
  }



  public static boolean isAllConsonantes(String str) {
    for (int i = 0 ; i < str.length() ; i++) {
      if (isVocal(str.charAt(i))) {
        return false ;
      }
    }
    return true ;

  }
  public static boolean isVocal(char ch) {
    return (
            ch == 'e' ||
                    ch == 'a' ||
                    ch == 'o' ||
                    ch == 'u' ||
                    ch == 'i' ||
                    ch == 'ä' ||
                    ch == 'ü' ||
                    ch == 'ö' ||

                    ch == 'E' ||
                    ch == 'A' ||
                    ch == 'O' ||
                    ch == 'U' ||
                    ch == 'I' ||
                    ch == 'Ä' ||
                    ch == 'Ü' ||
                    ch == 'Ö') ;
  }

  private static String arr2Str(String[] strArr , String paDelim) {
    StringBuffer sb = new StringBuffer();

    for (int i = 0 ; i < strArr.length ; i++) {
      sb.append(strArr[ i ]) ;
      if (i < (strArr.length - 1)) {
        sb.append(paDelim) ;
      }
    }
    return sb.toString();
  }
  private static boolean checkKnownSyllable(String strLast , String strNext) {
    System.out.println(strLast + "|" + strNext) ;
    if (endsWithKnownSyllable(strLast.toLowerCase()) && startsWithKnownSyllable(strNext)) {
      System.out.println("!!!");
      return true;
    }
    return false;

  }

  private static boolean endsWithKnownSyllable(String str) {
    Iterator<String> iter = syllHashSet.iterator();

    while (iter.hasNext()) {
      if (str.endsWith(iter.next())) {
        return true;
      }

    }
    return false;

  }

  private static boolean startsWithKnownSyllable(String str) {
    Iterator<String> iter = syllHashSet.iterator();

    while (iter.hasNext()) {
      if (str.startsWith(iter.next())) {
        return true;
      }
    }
    return false;

  }

}
