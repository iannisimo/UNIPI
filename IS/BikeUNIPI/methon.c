int CalcolaPagamento (int initpren, int finepren, int oraarrivo, int orarilascio, int tariffa_al_minuto) {
  if (tariffa_al_minuto < 0) return -1;
  if (finepren <= initpren) return -1;
  if (orarilascio <= oraarrivo) return -1;
  if (oraarrivo < initpren) return -1;
  if (orarilascio <= finepren) {
    return (finepren - initpren) * tariffa_al_minuto;
  }
  if (orarilascio <= (finepren + 20)) {
    int pagamento = (finepren - initpren) * tariffa_al_minuto;
    pagamento += pagamento/100*10;
    return pagamento;
  }
  if (orarilascio <= (finepren + 40)) {
    int pagamento = (finepren - initpren) * tariffa_al_minuto;
    pagamento += pagamento/100*10;
    pagamento += pagamento/100*20;
    return pagamento;
  }
  int pagamento = (finepren - initpren) * tariffa_al_minuto;
  pagamento += pagamento/100*10;
  pagamento += pagamento/100*20;
  pagamento += pagamento/100*70;
  return pagamento;
}
