import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';


@Component({
  standalone: true,                     // ✅ Mark as standalone
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],    // ✅ Correct property name
  imports: [RouterModule]                // ✅ Add imports for standalone component
})
export class AppComponent {
  title = 'angularapp';
}
