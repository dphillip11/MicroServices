import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GatewayService {
  private readonly GETNAMES_URL = 'http://localhost:8082/api/names';

  constructor(private http: HttpClient) { }

  getAllNames(): Observable<string[]> {
    return this.http.get<string[]>(this.GETNAMES_URL);
  }
}
