package objecte

// Coordenades dels objectes
type Coordenades struct {
	X int `bson:"x"`
	Y int `bson:"y"`
}

// Mou en la direcció especificada
func (p *Coordenades) Mou(moviment string) {
	switch moviment {
	case ">":
		p.X = p.X + 1
	case "<":
		p.X = p.X - 1
	case "v":
		p.Y = p.Y + 1
	case "^":
		p.Y = p.Y - 1
	}
}

// Objecte a carregar
type Objecte struct {
	ID        int32       `bson:"id"`
	Objecte   string      `bson:"objecte"`
	Posicio   Coordenades `bson:"posicio"`
	Moviments []string    `bson:"previsioMoviments"`
}
