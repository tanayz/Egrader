 Stanford POS Tagger  v3 14 - 2012-11-11 .
 Copyright -LRB- c -RRB- 2002-2012 The Board of Trustees of .
 The Leland Stanford Junior University .
 All Rights Reserved  .
 This document contains -LRB- some -RRB- information about the models included in .
 this release and.
 that may be downloaded for the POS tagger website at .
 http:\/\/nlpstanfordedu\/software\/taggershtml .
 If you have downloaded .
 the full tagger  all of the models mentioned in this document are in the .
 downloaded package in the same directory as this readme .
 Otherwise  .
 included in the download are two .
 English taggers  and the other taggers may be downloaded from the .
 website .
 All taggers are accompanied by the props files used to create .
 them ; please examine these files for more detailed information about the .
 creation of the taggers  .
 For English  the bidirectional taggers are slightly more accurate  but .
 tag much more slowly ; choose the appropriate tagger based on your .
 speed\/performance needs  .
 English taggers .
 --------------------------- .
 wsj-0-18-bidirectional-distsim .
 tagger .
 Trained on WSJ sections 0-18 using a bidirectional architecture and .
 including word shape and.
 distributional similarity features .
 Penn Treebank tagset .
 Performance : .
 9728 % correct on WSJ 19-21 .
 -LRB- 9046 % correct on unknown words -RRB-  .
 wsj-0-18-left3words .
 tagger .
 Trained on WSJ sections 0-18 using the left3words architecture and .
 includes word shape features .
 Penn tagset .
 Performance : .
 9697 % correct on WSJ 19-21 .
 -LRB- 8885 % correct on unknown words -RRB-  .
 wsj-0-18-left3words-distsim .
 tagger .
 Trained on WSJ sections 0-18 using the left3words architecture and .
 includes word shape and.
 distributional similarity features .
 Penn tagset .
 Performance : .
 9701 % correct on WSJ 19-21 .
 -LRB- 8981 % correct on unknown words -RRB-  .
 english-left3words-distsim .
 tagger .
 Trained on WSJ sections 0-18 and extra parser training data using the .
 left3words architecture and.
 includes word shape and distributional .
 similarity features .
 Penn tagset  .
 english-bidirectional-distsim .
 tagger .
 Trained on WSJ sections 0-18 using a bidirectional architecture and .
 including word shape and.
 distributional similarity features .
 Penn Treebank tagset  .
 wsj-0-18-caseless-left3words-distsim .
 tagger .
 Trained on WSJ sections 0-18 left3words architecture and.
 includes word .
 shape and distributional similarity features .
 Penn tagset .
 Ignores case  .
 english-caseless-left3words-distsim .
 tagger .
 Trained on WSJ sections 0-18 and extra parser training data using the .
 left3words architecture and.
 includes word shape and distributional .
 similarity features .
 Penn tagset .
 Ignores case   .
 Chinese tagger .
 --------------------------- .
 chinese-nodistsim .
 tagger .
 Trained on a combination of CTB7 texts from Chinese and.
 Hong Kong .
 sources .
 LDC Chinese Treebank POS tag set .
 Performance : .
 9346 % on a combination of Chinese and.
 Hong Kong texts .
 -LRB- 7940 % on unknown words -RRB-  .
 chinese-distsim .
 tagger .
 Trained on a combination of CTB7 texts from Chinese and.
 Hong Kong .
 sources with distributional similarity clusters .
 LDC Chinese Treebank POS tag set .
 Performance : .
 9399 % on a combination of Chinese and.
 Hong Kong texts .
 -LRB- 8460 % on unknown words -RRB-  .
 Arabic tagger .
 --------------------------- .
 arabic-accurate .
 tagger .
 Trained on the \* entire \* ATB p1-3 .
 When trained on the train part of the ATB p1-3 split done for the 2005 .
 JHU Summer Workshop -LRB- Diab split -RRB-  using -LRB- augmented -RRB- Bies tags  it gets .
 the following performance : .
 Performance : .
 9650 % on dev portion according to Diab split .
 -LRB- 8059 % on unknown words -RRB-  .
 arabic-fast .
 tagger .
 4x speed improvement over `` accurate '' .
 Performance : .
 9634 % on dev portion according to Diab split .
 -LRB- 8028 % on unknown words -RRB-   .
 French tagger .
 --------------------------- .
 french .
 tagger .
 Trained on the French treebank  .
 German tagger .
 --------------------------- .
 german-hgc .
 tagger .
 Trained on the first 80 % of the Negra corpus  which uses the STTS tagset .
 The Stuttgart-T� 1\/4 bingen Tagset -LRB- STTS -RRB- is a set of 54 tags for annotating .
 German text corpora with part-of-speech labels  which was jointly .
 developed by the Institut f� 1\/4 r maschinelle Sprachverarbeitung of the .
 University of Stuttgart and.
 the Seminar f� 1\/4 r Sprachwissenschaft of the .
 University of T� 1\/4 bingen .
 See : .
 http:\/\/wwwimsuni-stuttgartde\/projekte\/CQPDemos\/Bundestag\/help-tagsethtml .
 This model uses features from the distributional similarity clusters .
 built over the HGC .
 Performance : .
 9690 % on the first half of the remaining 20 % of the Negra corpus -LRB- dev set -RRB- .
 -LRB- 9033 % on unknown words -RRB-  .
 german-dewac .
 tagger .
 This model uses features from the distributional similarity clusters .
 built from the deWac web corpus  .
 german-fast .
 tagger .
 Lacks distributional similarity features  but is several times faster .
 than the other alternatives .
 Performance : .
 9661 % overall \/ 8672 % unknown .
