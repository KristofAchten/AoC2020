import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Component({
  selector: 'app-day3',
  templateUrl: './day3.component.html',
  styleUrls: ['./day3.component.css']
})
export class Day3Component implements OnInit {

  part1: string = ''
  part2: string = ''
  input: string[][] = []

  constructor(private http: HttpClient) {
    this.http.get("assets/day3.txt", { responseType: 'text'}).subscribe(data => {
      // Never split on \n, always include the carriage return. That's another 10 minutes of my life...
      let unprocessedInput = data.split('\r\n')

      for (let j = 0; j < unprocessedInput.length; j++) {
        let curLine = []

        for (let i = 0; i < unprocessedInput[j].length; i++) {         
          curLine.push(unprocessedInput[j].charAt(i))
        }

        this.input.push(curLine)
      }
    })
  }

  ngOnInit(): void {
  }

  determinePart1() {
    this.part1 = this.determineNumOfTreesForSlope(3, 1).toString()    
  }

  determinePart2() {
    this.part2 = (this.determineNumOfTreesForSlope(1, 1) *
      this.determineNumOfTreesForSlope(3, 1) * 
      this.determineNumOfTreesForSlope(5, 1) * 
      this.determineNumOfTreesForSlope(7, 1) * 
      this.determineNumOfTreesForSlope(1, 2))
    .toString()
  }

  determineNumOfTreesForSlope(difX: number, difY: number): number {
    let infoSet = {x: 0, y: 0, numOfTrees: 0}

    while (infoSet.y <= (this.input.length - difY)) {
      if (this.input[infoSet.y][infoSet.x] === "#") {
        infoSet.numOfTrees += 1
      }
      infoSet = {x: (infoSet.x + difX) % this.input[0].length, y: infoSet.y + difY, numOfTrees: infoSet.numOfTrees}
    }

    return infoSet.numOfTrees
  }
}
