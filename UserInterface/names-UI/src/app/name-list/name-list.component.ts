import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';

// Define an interface for the data type
interface NameItem {
  id: number;
  name: string;
}

@Component({
  selector: 'app-name-list',
  templateUrl: './name-list.component.html',
  styleUrls: ['./name-list.component.css']
})

export class NameListComponent implements OnInit {
  names: string[] = [];
  newName: string = '';  // This binds to the input field
  data: string = '';

  constructor(private http: HttpClient) {}


  ngOnInit() {
    this.fetchNames();
  }


  fetchNames() {
    this.http.get<string>('http://localhost:8082/names').subscribe(responseString => {
      const response = JSON.parse(responseString);
      this.data = responseString;
      this.names = response.map((item: NameItem) => item.name);
    }, error => {
      console.error("Failed to fetch names:", error);
      // Handle or display error to the user here
    });
  }

  addName() {
    if (this.newName) {
      // Here, send a POST request to the backend to add a new name, then refresh the list
      this.http.post('http://localhost:8082/names', { name: this.newName }).subscribe(() => {
        this.fetchNames();
      });
    }
  }

  removeName() {
    if (this.newName) {
      const options = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        }),
        body: {
          name: this.newName
        }
      };

      this.http.delete('http://localhost:8082/names', options).subscribe(() => {
        this.fetchNames();
      });
    }
  }
}
