import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';


function Square(props) {
    return (
    <button className="square" onClick={props.onClick}> 
        {props.value}
    </button>
    );
  }
  
class Board extends React.Component {
    
    renderSquare(i) {
        return (
            <Square 
            value = {this.props.squares[i]}
            onClick = {() => {this.props.onClick(i)}}
            />
        );
    }

    render() {
        return (
        <div>
            <div className="board-row">
            {this.renderSquare(0)}
            {this.renderSquare(1)}
            {this.renderSquare(2)}
            </div>
            <div className="board-row">
            {this.renderSquare(3)}
            {this.renderSquare(4)}
            {this.renderSquare(5)}
            </div>
            <div className="board-row">
            {this.renderSquare(6)}
            {this.renderSquare(7)}
            {this.renderSquare(8)}
            </div>
        </div>
        );
    }
}

class Game extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            history: [{squares: Array(9).fill(null)}],
            isX: true,
            stepNumber: 0,
            isComputer: 0,
        };
    }

    handleClick(i){
        const history = this.state.history.slice(0, this.state.stepNumber + 1)
        const current = history[history.length-1];
        const squares = current.squares.slice();
        if (!calculateWinner(squares) && !squares[i]){
            squares[i] = this.state.isX ? 'X' : 'O';
            this.setState({history : history.concat([{squares:squares}]), stepNumber: history.length, squares:squares, isX: !this.state.isX},this.checkComputerMove);
        }
    }

    checkComputerMove(){
        const history = this.state.history.slice(0, this.state.stepNumber + 1)
        const current = history[history.length-1];
        const squares = current.squares.slice();
        const mode = this.state.isComputer;
        const steps = this.state.stepNumber;
        if (mode > 0 && !this.isX && steps % 2 === 1){
            if (!calculateWinner(squares)){
                const computer = computerMove(squares, mode);
                if (computer !== null){
                    squares[computer] = 'O';
                    this.setState({history : history.concat([{squares:squares}]), stepNumber: history.length, squares:squares, isX: !this.state.isX});
                }
            }
        }
    }

    jumpTo(step) {
        this.setState({
          stepNumber: step,
          isX: (step % 2) === 0,
        }, this.checkComputerMove);
    }

    changeGameMode(i){
        this.setState({history: [{squares: Array(9).fill(null)}],
        isX: true,
        stepNumber: 0,
        isComputer: i});
    }

    render() {
        console.log("step number: " + this.state.stepNumber)
        const mode = this.state.isComputer;
        const history = this.state.history;
        const current = history[this.state.stepNumber];
        const winner = calculateWinner(current.squares);
        const title = "Tic-Tac-Toe built w/ React"
        const moves = history.map((step, move) => {
            const desc = move ?
                'Go to move #' + move :
                'Go to game start';
            return (
                <li key={move}>
                <button onClick={() => this.jumpTo(move)}>{desc}</button>
                </li>
            );
        });

        let status;
        if (winner){
            if (mode === -1){
                status = winner + " wins!";
            }else{
                status = "Computer wins!" ;
            }
        }else if (this.state.stepNumber === 9){
            status = "Draw!"
        }else{
            if (mode === -1){
                status = (this.state.isX ? 'X': 'O')+"'s Turn" ;
            }else{
                status = "Your Turn" ;
            }
        }

        if (mode === 0){
            return (
                <>
                <div className="title">{title}</div>
                <div className="game">
                    <button onClick={() => this.changeGameMode(-1)}>Player vs Player </button>
                    <button onClick={() => this.changeGameMode(1)}>Player vs Computer (Easy) </button>
                    <button onClick={() => this.changeGameMode(2)}>Player vs Computer (Hard) </button>
                </div>        
                <div className="bottom">made by adam wang</div>
            </>);
        }else if (mode === -1){ 
            return (
                <>
                <div className="title">{title}</div>
                <div className="game">
                    <button onClick={() => this.changeGameMode(-1)}>Player vs Player </button>
                    <button onClick={() => this.changeGameMode(1)}>Player vs Computer (Easy) </button>
                    <button onClick={() => this.changeGameMode(2)}>Player vs Computer (Hard) </button>
                </div>        
                <div className="game">
                    <div className="game-board">
                        <Board squares={current.squares} onClick={(i) => this.handleClick(i)} />
                    </div>
                    <div className="game-info">
                        <div>{status}</div>
                            <ul>{moves}</ul>
                    </div>
                </div>
                <div className="bottom">made by adam wang</div>
            </>);
        }
        else if (mode ===1){
            return (
                <>
                <div className="title">{title}</div>
                <div className="game">
                    <button onClick={() => this.changeGameMode(-1)}>Player vs Player </button>
                    <button onClick={() => this.changeGameMode(1)}>Player vs Computer (Easy) </button>
                    <button onClick={() => this.changeGameMode(2)}>Player vs Computer (Hard) </button>
                </div>        
                <div className="game">
                   <div className="game-board">
                               <Board squares={current.squares} onClick={(i) => this.handleClick(i)} />
                           </div>
                           <div className="game-info">
                               <div>{status}</div>
                               <ul>{moves}</ul>
                           </div>
                </div>
                <div className="bottom">made by adam wang</div>
            </>);
        }else{
            return (
                <>
                <div className="title">{title}</div>
                <div className="game">
                    <button onClick={() => this.setState({isComputer:-1})}>Player vs Player </button>
                    <button onClick={() => this.setState({isComputer:1})}>Player vs Computer (Easy) </button>
                    <button onClick={() => this.setState({isComputer:2})} >Player vs Computer (Hard) </button>
                </div>        
                <div className="game">
                   <div className="game-board">
                               <Board squares={current.squares} onClick={(i) => this.handleClick(i)} />
                           </div>
                           <div className="game-info">
                               <div>{status}</div>
                               <ul>{moves}</ul>
                           </div>
                </div>
                <div className="bottom">made by adam wang</div>
            </>);
        }
    }
}

function computerMove(squares, difficulty){
    var spot = null
    if (difficulty === 1){
        spot = parseInt(Math.random()*9);
        while (squares[spot] !==  null){
            spot = parseInt(Math.random()*9);
        }
        return spot;
    }else{
        if (squares[4] === null){
            return 4;
        }else{
            for (let i = 0; i < 9; i ++){
                var next = squares.slice();
                console.log(i)
                if (squares[i] === null){
                    next[i] = 'O'
                    if (calculateWinner(next) !== null){
                        console.log("can win")
                        return i
                    }
                }
            }
            for (let i = 0; i < 9; i ++){
                next = squares.slice();
                console.log(i)
                if (squares[i] === null){
                    next[i] = 'X'
                    if (calculateWinner(next) !== null){
                        console.log("can lose")
                        return i
                    }
                }
            }
            
            if ((squares[2] === 'X' && squares[6] === 'X') || (squares[0] === 'X' && squares[8] === 'X')){
                for (let j = 1; j < 8; j +=2){
                    if (squares[j] === null){
                       return j;
                    }
                }
            }
            if (squares[4] === 'O' && (squares[0] === 'X' || squares[8] === 'X')){
                if (squares[3] === null && squares[7] === null){
                    if (squares[6] === null){
                        return 6
                    }
                }
                if (squares[1] === null && squares[5] === null){
                    if (squares[2] === null){
                        return 2
                    }
                }
            }
            if (squares[4] === 'O' && (squares[2] === 'X' || squares[6] === 'X')){
                if (squares[3] === null && squares[1] === null){
                    if (squares[0] === null){
                        return 0
                    }
                }
                if (squares[7] === null && squares[5] === null){
                    if (squares[8] === null){
                        return 8
                    }
                }
            }
            for (let j = 0; j < 9; j +=2){
                if (squares[j] === null){
                   return j;
                }
            }                   
        }
    }
    return null
}

function calculateWinner(squares){
    const lines = [
        [0,1,2],
        [3,4,5],
        [6,7,8],
        [0,3,6],
        [1,4,7],
        [2,5,8],
        [0,4,8],
        [2,4,6],
    ];
    for (let i = 0; i<lines.length; i ++){
        const [a,b,c] = lines[i];
        if (squares[a] && squares[a] === squares[b] && squares[b] === squares[c]){
            return squares[a];
        }
    }
    return null;
}


// ========================================

ReactDOM.render(
<Game />,
document.getElementById('root')
);
  