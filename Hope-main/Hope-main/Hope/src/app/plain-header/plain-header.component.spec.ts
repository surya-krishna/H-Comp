import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlainHeaderComponent } from './plain-header.component';

describe('PlainHeaderComponent', () => {
  let component: PlainHeaderComponent;
  let fixture: ComponentFixture<PlainHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlainHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlainHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
