import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-day1',
  templateUrl: './day1.component.html',
  styleUrls: ['./day1.component.css']
})
export class Day1Component implements OnInit {

  part1: string = ''
  part2: string = ''
  input: number[] = []

  constructor(private http: HttpClient) {
    this.http.get("assets/day1.txt", { responseType: 'text'}).subscribe(data =>{
      this.input = data.split('\n').map(x=>+x);
    })
  }

  ngOnInit(): void {
  }

  determinePart1() {
    for (let num of this.input) {
      let res = 2020 - num   
      if (this.input.includes(res)) {
        this.part1 = String((num * (2020 - num)))
        return
      }
    }
    this.part1 = 'Could not determine solution!'
  }

  determinePart2() {
    for (var x = 0; x < this.input.length; x++) {
      for (var y = x; y < this.input.length; y++) {
        let res = 2020 - this.input[x] - this.input[y]
        if (this.input.includes(res)) {
          this.part2 = String(this.input[x] * this.input[y] * (2020 - this.input[x] - this.input[y]))
          return
        }
      }
    }
    this.part2 = 'Could not determine solution!'
  }
}
