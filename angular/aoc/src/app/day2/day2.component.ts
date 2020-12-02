import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Component({
  selector: 'app-day2',
  templateUrl: './day2.component.html',
  styleUrls: ['./day2.component.css']
})
export class Day2Component implements OnInit {

  part1: string = ''
  part2: string = ''
  input: string[] = []

  constructor(private http: HttpClient) {
    this.http.get("assets/day2.txt", { responseType: 'text'}).subscribe(data =>{
      this.input = data.split('\n');
    })
  }

  ngOnInit(): void {
  }

  determinePart1() {
    let comp = (min: number, max: number, c: string, s: string): number => {
      var count = (s.match(new RegExp(c, 'g')) || []).length;
      return (count >= min && count <= max) ? 1 : 0
    }

    this.part1 = this.determineValidPasswords(comp).toString()
  }

  determinePart2() {
    let comp = (min: number, max: number, c: string, s: string): number => {
      var firstCond = min - 1 < s.length && s[min - 1] === c
      var sndCond = max - 1 < s.length && s[max - 1] === c
      return (firstCond !== sndCond) ? 1 : 0
    }

    this.part2 = this.determineValidPasswords(comp).toString()
  }

  determineValidPasswords(comp: (min: number, max: number, c: string, s: string) => number): string {
    let cnt: number = 0
    for (let def of this.input) {
      let parts = def.replace(":", "").split(" ")
      let minMax = parts[0].split("-")

      cnt += comp(Number(minMax[0]), Number(minMax[1]), parts[1], parts[2])
    }

    return cnt.toString()
  }
}
