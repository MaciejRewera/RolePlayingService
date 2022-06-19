import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Button from 'react-bootstrap/Button';

function App() {
  return (
    <Container className="p-3">
      <Row>
        <h1>Welcome to WFRP IV edition</h1>
      </Row>
      <Row>
        <h2>Here you can create your character</h2>
      </Row>
      <Row>
        <Button className="btn btn-primary">Enter Initial Characteristics</Button>
      </Row>
    </Container>
  );
}

export default App;
