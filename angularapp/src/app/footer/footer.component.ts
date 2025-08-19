import { Component, HostListener } from '@angular/core';


@Component({
  selector: 'app-footer',
  imports:[],
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css'],
})
export class FooterComponent {
  scale = 1;
  opacity = 1;

  @HostListener('window:scroll', [])
  onScroll() {
    const scrollPosition = window.scrollY || document.documentElement.scrollTop;
    const maxScroll = document.documentElement.scrollHeight - window.innerHeight;

    this.scale = 1 + (scrollPosition / maxScroll) * 0.5; // Scale up to 1.5
    this.opacity = 1 - (scrollPosition / maxScroll) * 0.5; // Fade out to 0.5
  }
}
