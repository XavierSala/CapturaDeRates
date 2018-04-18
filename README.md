## La captura de les rates

Una fàbrica de formatges té una plaga de rates que els està deixant sense existències. El problema és que com que fabriquen formatges no poden fer servir verí per eliminar les rates…

![Formatges](imatges/rates0.png)

Per això ha decidit contractar l’empresa ecològica de liquidació de plagues especialitzada en rates: “CAT ALLUNYA, SL”. Aquesta empresa fa servir gats per eliminar les rates.

![Rata](imatges/rates2.png)

Després d’una anàlisi exhaustiva, i amb l’ajuda d’un model d’intel·ligència artificial, l’empresa ja ha descobert que es poden determinar quines són les rutes que seguiran cada una de les rates (abans que les mateixes rates ho sàpiguen!).

    {
    "id" : 1,  "objecte" : "rata",  "posicio" : { "x" : 0, "y" : 1 }, "nom" : "Rata1",
    "previsioMoviments" : [  "^", ">", "v", "v", ">", ">" ]
    }
    {
    "id" : 2,  "objecte" : "rata",  "posicio" : { "x" : 4, "y" : 0 }, "nom" : "Rata2",
    "previsioMoviments" : [ "<", "<", "<", "v", "v" ]
    }
    {
    "id" : 3,  "objecte" : "rata",  "posicio" : { "x" : 0, "y" : 1 }, "nom" : "Rata3",
    "previsioMoviments" : [ "^", ">", "v", "v", ">", ">"]
    }

![recorregut](imatges/rates3.png)

Tenint en compte que els gats d’aquesta empresa són molt efectius i capturen tot el que els hi passa pel costat però que, degut a l’èxit de l’empresa, estan molt grassos i quan els posen en un lloc no se’n mouen per res i només capturen les rates que vagin allà...

![gats estàtics](imatges/rates4.png)

## Tasca

Tenint en compte el número de gats que han portat a la fàbrica i el lloc on els han deixat, feu un programa que determini quants formatges es menjaran les rates i quantes rates seran atrapades pels gats

![Rata capturada](imatges/rates5.png)

    Tota la informació (els moviments de les rates, on són els gats i els formatges) s’ha anat emmagatzemant en una base de dades MongoDB

    Per fer aquesta tasca caldrà tenir la col·lecció ‘rates’ importada en un servidor MongoDB [Descarrear]000(https://drive.google.com/file/d/1F3pKFVCnI3kAQmfMp_HmqmbGp58qbc_f/view?usp=sharing)

    Es pot importar fent servir la comanda mongoimport:

    $ mongoimport --db=poble --collection=rates
                --host 172.17.0.3 rates.json
                --jsonArray