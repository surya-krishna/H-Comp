import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestTimelineComponent } from './test-timeline.component';

describe('TestTimelineComponent', () => {
  let component: TestTimelineComponent;
  let fixture: ComponentFixture<TestTimelineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestTimelineComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestTimelineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
