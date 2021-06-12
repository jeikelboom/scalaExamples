val nihil = 0.0F
val centFloat = 0.01F
val tenCent = (1 to 10).foldLeft(nihil)((accu, _) => accu + centFloat)
val thousandEuro = (1 to 100000).foldLeft(nihil)((accu, _) => accu + centFloat)
val quarters = (1 to 100000).foldLeft(nihil)((accu,_) => accu + 0.25F)

