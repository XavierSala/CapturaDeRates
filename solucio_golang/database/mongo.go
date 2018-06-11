package database

import (
	"log"

	o "../model"
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

// DB és la Base de dades Mongo
type DB struct {
	mongo      *mgo.Session
	dbname     string
	collection string
}

// Connecta serveix per definir connexions amb la base de dades
func (m *DB) Connecta(host string, port int, dbname string, collection string) (bool, error) {
	var err error
	m.mongo, err = mgo.Dial(host)
	if err != nil {
		return false, err
	}
	m.dbname = dbname
	m.collection = collection
	// defer m.mongo.Close()
	return true, nil
}

// Quantes determina quantes instàncies hi ha d'un objecte  determinat.
func (m *DB) Quantes(tipus string) (int, error) {
	c := m.mongo.DB(m.dbname).C(m.collection)

	quants, err := c.Find(bson.M{"objecte": tipus}).Count()
	if err != nil {
		return -1, err
	}
	return quants, nil
}

// BuscaElements cerca els elements d'un determinat tipus
func (m *DB) BuscaElements(tipus string) []o.Objecte {
	c := m.mongo.DB(m.dbname).C(m.collection)

	var resultats []o.Objecte
	err := c.Find(bson.M{"objecte": tipus}).All(&resultats)
	if err != nil {
		log.Fatal(err)
	}
	return resultats
}

// ObtenirDimensions calcula les dimensions del tauler
func (m *DB) ObtenirDimensions() (int, int, error) {
	c := m.mongo.DB(m.dbname).C(m.collection)

	var resultat o.Objecte

	err := c.Find(nil).Sort("-posicio.x").One(&resultat)
	if err != nil {
		return -1, -1, err
	}
	x := resultat.Posicio.X

	err = c.Find(nil).Sort("-posicio.y").One(&resultat)
	if err != nil {
		return x, -1, err
	}
	y := resultat.Posicio.Y

	return x, y, nil
}
