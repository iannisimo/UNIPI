

// mese ha valore compreso tra 0  e 11 con:
// gennaio:  0
// .
// .
// .
// dicembre: 11\

int dbQuery(int mese, int ombrelloni, int gazebi, int sdraio, int lettini, int sedie, int cabine, int docce, int pAuto) {
  int obrPrice[] = {0, 0, 0, 0, 5, 10, 10, 13, 7, 5, 0, 0};
  int gzbPrice[] = {0, 0, 0, 0, 7, 12, 12, 15, 8, 7, 0, 0};
  int sdrPrice[] = {0, 0, 0, 0, 1, 3, 3, 5, 2, 1, 0, 0};
  int lttPrice[] = {0, 0, 0, 0, 1, 3, 3, 5, 2, 1, 0, 0};
  int sdePrice[] = {0, 0, 0, 0, 1, 3, 3, 5, 2, 1, 0, 0};
  int cbnPrice[] = {0, 0, 0, 0, 5, 8, 8, 10, 7, 5, 0, 0};
  int dccPrice[] = {0, 0, 0, 0, 3, 5, 5, 7, 4, 3, 0, 0};
  int pAtPrice[] = {0, 0, 0, 0, 4, 8, 8, 10, 6, 4, 0, 0};
  return
    obrPrice[mese] * ombrelloni +
    gzbPrice[mese] * gazebi +
    sdrPrice[mese] * sdraio +
    lttPrice[mese] * lettini +
    sdePrice[mese] * sedie +
    cbnPrice[mese] * cabine +
    dccPrice[mese] * docce +
    pAtPrice[mese] * pAuto;
}

int calcolaPrezzo(int mese, int ombrelloni, int gazebi, int sdraio, int lettini, int sedie, int cabine, int docce, int pAuto) {
  if (mese < 4 || mese > 9)           return -1;
  if (ombrelloni < 0)                 return -1;
  if (gazebi < 0)                     return -1;
  if (sdraio < ombrelloni*2)          return -1;
  if (lettini < gazebi*2)             return -1;
  if (sedie < 0)                      return -1;
  if (cabine < 0)                     return -1;
  if (docce < 0)                      return -1;
  if (pAuto < 0)                      return -1;

  if (cabine > ombrelloni + gazebi)   return -1;
  if (docce > cabine)                 return -1;
  sdraio -= ombrelloni*2;
  lettini -= gazebi*2;
  return dbQuery(mese, ombrelloni, gazebi, sdraio, lettini, sedie, cabine, docce, pAuto);
}
