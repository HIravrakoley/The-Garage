import { Component } from '@angular/core';
import { HomenavbarComponent } from '../../homenavbar/homenavbar.component';

@Component({
  selector: 'app-homepage',
  imports: [HomenavbarComponent],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent {
  carImages: string[] = [
    'car3.jpg'
  ];
  currentIndex: number = 0;

}
