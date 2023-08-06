import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'app-name-list',
  templateUrl: './name-list.component.html',
  styleUrls: ['./name-list.component.css']
})

export class NameListComponent implements OnInit {
  names: string[] = [];
  newName: string = '';  // This binds to the input field

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchNames();
  }

  fetchNames() {
    this.http.get('http://localhost:8082/api/names').subscribe((response: any) => {
      this.names = response.data;
    });
  }

  addName() {
    if (this.newName) {
      // Here, send a POST request to the backend to add a new name, then refresh the list
      this.http.post('http://localhost:8082/api/names', { name: this.newName }).subscribe(() => {
        this.fetchNames();
      });
      this.newName = '';
    }
  }

  removeName() {
    if (this.newName) {
      // Here, send a DELETE request to the backend to remove the name, then refresh the list
      this.http.delete(`http://localhost:8082/api/names/${this.newName}`).subscribe(() => {
        this.fetchNames();
      });
      this.newName = '';
    }
  }
}
