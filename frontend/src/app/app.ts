import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TestService } from './services/test-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet ,FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
 message = '';
  name = 'Omar';

  constructor(private testService: TestService) {}

  ngOnInit() {
    this.testService.getHello().subscribe({
      next: (response) => {
        this.message = response.message;
        console.log('Backend response:', response);
      },
      error: (err) => console.error('Error calling backend:', err)
    });
  }

  callWithName() {
    this.testService.getHelloWithName(this.name).subscribe({
      next: (response) => {
        this.message = response.message;
      }
    });
  }
}
