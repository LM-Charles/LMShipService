/**
 * Created by desmondz on 7/26/2015.
 */
var EditorWithState = React.createClass({
    getInitialState(){
        var client = new DeferralEditorAjaxClient();
        return {
            showUpdateOrderStatus: false
        };
    },
    closeUpdateOrderStatus(){
        this.setState({
            showUpdateOrderStatus: false
        });
    },
    updateOrderStatusConfirm(){
        this.closeUpdateOrderStatus();
    },
    openUpdateOrderStatus(){
        this.setState({
            showUpdateOrderStatus: true
        });
    },
    render() {
        var mockOrder = "{\n    \"client\" : \"{{current_user}}\",\n    \"orderDate\" : \"2012-04-23T18:25:43.511Z\",\n    \"fromAddress\" : {\n        \"address\" : \"8000 Delsom Way\",\n        \"address2\" : \"unit 10\",\n        \"city\" : \"Delta\",\n        \"province\" : \"BC\",\n        \"postal\" : \"V4C0A9\",\n        \"country\" : \"Canada\"\n    },\n    \"toAddress\" : {\n        \"address\" : \"9188 Hemlock Drive\",\n        \"city\" : \"Richmond\",\n        \"province\" : \"BC\",\n        \"postal\" : \"V7C2X4\",\n        \"country\" : \"Canada\"\n    },\n    \"serviceName\" : \"UPS_STANDARD\",\n    \"shipments\" : [\n        {\n            \"height\" : 1,\n            \"width\" : 1,\n            \"length\" : 1,\n            \"weight\" : 1,\n            \"nickName\" : \"mom's ring\",\n            \"shipmentPackageType\" : \"CUSTOM\"\t\n\t\t\t\"trackingNumber\": \"2314315423524352\",\n\t\t\t\"trackingDocumentType\": null,\n\t\t\t\"tracking\" : {\n\t\t\t  \"pickUpDate\": 1262908800000,\n\t\t\t  \"trackingDate\": 1263081600000,\n\t\t\t  \"trackingCity\": \"ANYTOWN\",\n\t\t\t  \"trackingCountry\": \"US\",\n\t\t\t  \"trackingStatus\": \"DELIVERED\"\n\t\t\t}\t\n        },\n        {\n            \"height\" : 100,\n            \"width\" : 100,\n            \"length\" : 100,\n            \"weight\" : 100,\n            \"nickName\" : \"television\",\n            \"shipmentPackageType\" : \"SMALL\"\t\n\t\t\t\"trackingNumber\": \"2314315423524352\",\n\t\t\t\"trackingDocumentType\": null,\n\t\t\t\"tracking\" : {\n\t\t\t  \"pickUpDate\": 1262908800000,\n\t\t\t  \"trackingDate\": 1263081600000,\n\t\t\t  \"trackingCity\": \"ANYTOWN\",\n\t\t\t  \"trackingCountry\": \"US\",\n\t\t\t  \"trackingStatus\": \"DELIVERED\"\n\t\t\t}\n        }\n    ],\n    \"handler\" : \"optional_handler_name\",\n    \"goodCategoryType\" : \"COSMETICS\",\n    \"declareValue\" : 100,\n    \"insuranceValue\" : 150,\n    \"appointmentDate\" : 1263110400000,\n    \"appointmentSlotType\" : \"MORNING\"\n\t\"status\" : {\n\t\t\"status\" : \"IN_TRANSIT\",\n\t\t\"description\" : \"Something about the overall status\"\n\t\t\"handler\" : \"can be used to store the person who made the update\"\n\t\t\"date\" : 1263110400000\n\t}\n}";
        var mockShipment = "{\n            \"height\" : 1,\n            \"width\" : 1,\n            \"length\" : 1,\n            \"weight\" : 1,\n            \"nickName\" : \"mom's ring\",\n            \"shipmentPackageType\" : \"CUSTOM\"\t\n\t\t\t\"trackingNumber\": \"2314315423524352\",\n\t\t\t\"trackingDocumentType\": null,\n\t\t\t\"tracking\" : {\n\t\t\t  \"pickUpDate\": 1262908800000,\n\t\t\t  \"trackingDate\": 1263081600000,\n\t\t\t  \"trackingCity\": \"ANYTOWN\",\n\t\t\t  \"trackingCountry\": \"US\",\n\t\t\t  \"trackingStatus\": \"DELIVERED\"\n\t\t\t}\t\n        }";
        var queryShipmentButton = (
            <Button bsStyle="primary">Query Shipment</Button>
        );

        return (
            <div className="component-EditorWithState-container">
                <h3>LM Administrative Website</h3>
                <Row>
                    <Col md="6"><Input type='text' label='Username'/></Col>
                    <Col md="6"><Input type='password' label='Password'/></Col>
                </Row>
                <Row>
                    <Col md="12"> <Input type='text' label='Search for Order by ID' placeholder='Enter Order ID'
                                         buttonAfter={queryShipmentButton}/></Col>
                </Row>
                <Row>
                    <Col md="12">
                        <Panel collapsible header="&#9654; Order Details for #11241498">
                            <textarea rows="20">
                                {mockOrder}
                            </textarea>
                        </Panel>
                    </Col>
                </Row>
                <Row>
                    <Col mdOffset="1" md="11">
                        <div>
                            <Panel collapsible header="&#9654; Shipment #1: Mom's Ring" eventKey="1">
                        <textarea rows="15">
                                {mockShipment}
                        </textarea>
                                <Input label='Update Tracking Number'>
                                    <Input type='text' placeholder='Enter Tracking Number'/>
                                    <Button>Update Tracking</Button>
                                </Input>
                                <Input label='Update Dimension'>
                                    <Row>
                                        <Col md="4"><Input type='text' placeholder='Length'/></Col>
                                        <Col md="4"><Input type='text' placeholder='Width'/></Col>
                                        <Col md="4"><Input type='text' placeholder='Height'/></Col>
                                    </Row>
                                    <Row>
                                        <Col md="12">
                                            <Input type='select' placeholder='Packaging'>
                                                <option value='CUSTOM'>Self Package</option>
                                                <option value='SMALL'>Small Box</option>
                                                <option value='MEDIUM'>Medium Box</option>
                                                <option value='LARGE'>Large Box</option>
                                            </Input>
                                        </Col>
                                        <Col md="12"> <Input type='text' placeholder='Weight'/></Col>
                                    </Row>
                                    <Button>Update Dimension</Button>
                                </Input>
                            </Panel>
                        </div>
                    </Col>
                </Row>
                <Row>
                    <Col md="12">
                        <Panel>
                            <Row>
                                <Col md="2"><Button className="fill">Re-calculate Rate...</Button></Col>
                                <Col md="2"><Button className="fill" onClick={this.openUpdateOrderStatus}>Update
                                    Status...</Button></Col>
                            </Row>
                        </Panel>
                    </Col>
                </Row>
                <Modal show={this.state.showUpdateOrderStatus} onHide={this.closeUpdateOrderStatus}>
                    <Modal.Header closeButton>
                        <Modal.Title>Update Order Status</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Input label="New Status" type='select' placeholder="Select status">
                            <option value='PENDING_PICKUP'>Pending Pickup</option>
                            <option value='PROCESSING'>Processing</option>
                            <option value='IN_TRANSIT'>In Transit</option>
                            <option value='EXCEPTION'>Exception</option>
                            <option value='COMPLETE'>Complete</option>
                        </Input>
                        <Input label="Message" type='textarea' placeholder='Describe your change'/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Row>
                            <Col md="6"><Button onClick={this.closeUpdateOrderStatus}>Close</Button></Col>
                            <Col md="6"><Button bsStyle="primary" onClick={this.updateOrderStatusConfirm}>Save</Button></Col>
                        </Row>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
});