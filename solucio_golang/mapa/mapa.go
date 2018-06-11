package mapa

import (
	"errors"
	"fmt"
	"log"

	o "../model"
)

// Mapa representa la planta de l'edifici
type Mapa struct {
	mapa             [][]o.Objecte
	files            int
	columnes         int
	FormatgesMenjats int
	RatesMenjades    int
}

// Crea el mapa amb les dimensions especificades
func (m *Mapa) Crea(files, columnes int) {
	m.files = files
	m.columnes = columnes
	m.mapa = make([][]o.Objecte, files)
	for i := 0; i < files; i++ {
		m.mapa[i] = make([]o.Objecte, columnes)
	}
}

// Posa entra els objectes al mapa
func (m *Mapa) Posa(objectes []o.Objecte) (bool, error) {
	for _, objecte := range objectes {
		if m.estaADins(objecte.Posicio) {
			m.mapa[objecte.Posicio.X][objecte.Posicio.Y] = objecte
		} else {
			return false, errors.New("Un dels objectes no està a dins")
		}
	}
	return true, nil
}

// MouRates mou les rates pel mapa
func (m *Mapa) MouRates(rates []o.Objecte) {
	for _, rata := range rates {
		m.mouRata(rata)
	}
}

// mouRata mou una sola rata en la direcció especificada
func (m *Mapa) mouRata(rata o.Objecte) {

	rataMorta := false

	for _, moviment := range rata.Moviments {
		rata.Posicio.Mou(moviment)
		if m.estaADins(rata.Posicio) {
			switch m.mapa[rata.Posicio.X][rata.Posicio.Y].Objecte {
			case "gat":
				m.RatesMenjades++
				rataMorta = true
				log.Printf("...Rata %d menjada per Gat %d (%d)", rata.ID, m.mapa[rata.Posicio.X][rata.Posicio.Y].ID, m.RatesMenjades)
			case "formatge":
				m.mapa[rata.Posicio.X][rata.Posicio.Y] = rata
				m.FormatgesMenjats++
				log.Printf("...Formatge menjat per Rata %d (%d)", rata.ID, m.FormatgesMenjats)
			default:
				m.mapa[rata.Posicio.X][rata.Posicio.Y] = rata
			}
		}
		if rataMorta == true {
			break
		}
	}
}

// Determina si la rata està a dins del mapa o no
func (m *Mapa) estaADins(posicio o.Coordenades) bool {
	return (posicio.X >= 0 && posicio.X < m.columnes && posicio.Y >= 0 && posicio.Y < m.files)
}

// Imprimeix el mapa en ASCII
func (m *Mapa) Imprimeix() {
	for fila := 0; fila < len(m.mapa); fila++ {
		for columna := 0; columna < len(m.mapa[fila]); columna++ {
			que := m.mapa[columna][fila].Objecte
			if que != "" {
				fmt.Print(que[:1])
			} else {
				fmt.Print(".")
			}
		}
		fmt.Println()
	}
}
