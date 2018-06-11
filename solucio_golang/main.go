package main

import (
	"flag"
	"fmt"

	db "./database"
	mapa "./mapa"
)

const (
	host       = "127.0.0.1"
	port       = 27017
	dbname     = "fabrica"
	collection = "rates7"
)

const (
	ample = 10
	alt   = 10
)

func main() {
	var connexio db.DB

	server := flag.String("h", host, "Host to connect")
	tcpport := flag.Int("p", port, "Port to connect")
	basedata := flag.String("db", dbname, "Database name to use")
	colection := flag.String("c", collection, "Name of the collection to use")
	flag.Parse()

	_, err := connexio.Connecta(*server, *tcpport, *basedata, *colection)
	checkErr(err)

	numRates, err := connexio.Quantes("rata")
	checkErr(err)
	numGats, err := connexio.Quantes("gat")
	checkErr(err)
	numFormatges, err := connexio.Quantes("formatge")
	checkErr(err)

	fmt.Println("Rates:", numRates, " Gats:", numGats, " Formatges", numFormatges)

	x, y, err := connexio.ObtenirDimensions()
	checkErr(err)

	var mapa mapa.Mapa
	mapa.Crea(x+1, y+1)

	_, err = mapa.Posa(connexio.BuscaElements("gat"))
	checkErr(err)
	_, err = mapa.Posa(connexio.BuscaElements("formatge"))
	checkErr(err)
	rates := connexio.BuscaElements("rata")
	checkErr(err)

	_, err = mapa.Posa(rates)

	// mapa.Imprimeix()
	mapa.MouRates(rates)
	fmt.Printf("S'han menjat %d rates i %d formatges\n", mapa.RatesMenjades, mapa.FormatgesMenjats)

}

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}
